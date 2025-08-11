package com.github.navelogic.estudiovirtualapi.Util.API;

import com.github.navelogic.estudiovirtualapi.Model.Finance.InflationIndex;
import com.github.navelogic.estudiovirtualapi.Repository.InflationIndexRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class InflationAPI {

    private final InflationIndexRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(InflationAPI.class);

    private static final Pattern entryPattern = Pattern.compile(
            "\\{\\s*\"SERCODIGO\"\\s*:\\s*\"PRECOS12_IPCAG12\"\\s*,\\s*\"VALDATA\"\\s*:\\s*\"(\\d{4}-\\d{2}-\\d{2})T[^\"]*\"\\s*,\\s*\"VALVALOR\"\\s*:\\s*(\\d+(\\.\\d+)?)"
    );

    @Scheduled(fixedDelay = 86400000)
    @Transactional
    public void fetchInflationData() {
        logger.info("Iniciando atualização da série histórica IPCA...");

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://ipeadata.gov.br/api/odata4/ValoresSerie(SERCODIGO='PRECOS12_IPCAG12')"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.warn("Erro ao buscar dados. Código HTTP: {}", response.statusCode());
                return;
            }

            String responseBody = response.body();
            Matcher matcher = entryPattern.matcher(responseBody);

            int count = 0;

            while (matcher.find()) {
                String dateStr = matcher.group(1);
                String valueStr = matcher.group(2);

                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
                BigDecimal value = new BigDecimal(valueStr);

                if (repository.findByReferenceDate(date).isPresent()) {
                    logger.debug("Registro já existente para {}. Ignorando...", date);
                    continue;
                }

                InflationIndex index = InflationIndex.builder()
                        .referenceDate(date)
                        .value(value)
                        .build();

                repository.save(index);
                count++;
                logger.info("Salvo IPCA: {} => {}", date, value);
            }

            logger.info("Atualização finalizada. Novos registros salvos: {}", count);

        } catch (Exception e) {
            logger.error("Erro ao processar dados da inflação", e);
        }
    }
}
