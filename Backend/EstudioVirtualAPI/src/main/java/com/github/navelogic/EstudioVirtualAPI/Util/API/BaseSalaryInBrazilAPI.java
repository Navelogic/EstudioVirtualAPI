package com.github.navelogic.estudiovirtualapi.Util.API;

import com.github.navelogic.estudiovirtualapi.Model.Finance.BaseSalaryInBrazil;
import com.github.navelogic.estudiovirtualapi.Repository.BaseSalaryInBrazilRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BaseSalaryInBrazilAPI {

    private final BaseSalaryInBrazilRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(BaseSalaryInBrazilAPI.class);


    @Scheduled(fixedDelay = 2678400000L)
    @Transactional
    public void fetchAndSaveBaseSalary() {
        logger.info("Iniciando atualização do salário mínimo: " + LocalDateTime.now());

        try {
            Document doc = Jsoup.connect("https://www.ipeadata.gov.br/ExibeSerie.aspx?serid=1739471028").get();
            Elements rows = doc.select("tr[id^=grd_DXDataRow]");

            for (Element row : rows) {
                Elements columns = row.select("td.dxgv");

                String dateStr = columns.get(0).text();
                String valueStr = columns.get(1).text();

                if (dateStr.compareTo("1991.02") >= 0) {
                    int year = Integer.parseInt(dateStr.split("\\.")[0]);
                    int month = Integer.parseInt(dateStr.split("\\.")[1]);

                    // Verifica se já existe o registro no banco
                    boolean exists = repository.findByYearAndMonth(year, month).isPresent();
                    if (exists) {
                        logger.debug("Registro já existente para {}/{}. Pulando...", month, year);
                        continue;
                    }

                    BigDecimal value = parseBrazilianDecimal(valueStr);

                    BaseSalaryInBrazil salary = BaseSalaryInBrazil.builder()
                            .year(year)
                            .month(month)
                            .value(value)
                            .build();

                    repository.save(salary);
                    logger.info("Salário salvo: {}/{} -> {}", month, year, value);
                }
            }

            logger.info("Atualização concluída com sucesso.");

        } catch (Exception e) {
            logger.error("Erro ao atualizar dados do salário mínimo", e);
        }
    }

    private BigDecimal parseBrazilianDecimal(String valueStr) throws ParseException, java.text.ParseException {
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        Number number = nf.parse(valueStr);
        return new BigDecimal(number.toString());
    }
}
