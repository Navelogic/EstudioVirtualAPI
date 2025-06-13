package com.github.navelogic.estudiovirtualapi.Util.AIService;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class NameGeneratorService {

    private static final Random random = new Random();

    private static final List<String> STUDIO_PREFIXES = Arrays.asList(
            "Aurora", "Brisa", "Claraboia", "Caleidoscópio", "Origem", "Radiante", "Alvorada", "Miragem",
            "Luminosa", "Astro", "Épico", "Icone", "Arquetipo", "Sinapse", "Nimbus", "Zeppelin", "Vértice",
            "Arcanjo", "Metrópole", "Atlântico", "Tropikos", "Orbe", "Caravela", "Horizonte", "Maré", "Atlântida",
            "Savanna", "Ipiranga", "Cariri", "Ibiúna", "Ybyrá", "Tapajós", "Caruá", "Urucum", "Mandacaru", "Uirapuru",
            "Cacau", "Guaraná", "Aquarela", "Encanto", "Odisséia", "Lenda", "Estigma", "Verve", "Veredas", "Vagalume",
            "Infinito", "Solar", "Nômade", "Éden", "Orum", "Egrégora", "Quintessência", "Êxtase", "Zênite", "Obsidiana",
            "Neon", "Cítrica", "Quasar", "Vector", "Glitch", "Drone", "Criptico", "Nimbo", "Cipher", "Versátil",
            "Prisma", "Oásis", "Aether", "Labirinto", "Quimera", "Chama", "Fulgor", "Nimbus", "Singular", "Porta", "Jovem"
    );

    private static final List<String> STUDIO_SUFFIXES = Arrays.asList(
            "Studios", "Filmes", "Pictures", "Produções", "Media", "Cinema", "Narrativas", "Criações", "Dreamworks",
            "Motion", "Imagem", "Frames", "Visão", "Imagine", "Tela", "Reel", "Roteiro", "Cenas", "Ateliê", "Núcleo",
            "Trama", "Casa Criativa", "Coletivo", "Fábrica", "Oficina", "Guilda", "Estúdio Criativo", "Fronteira",
            "Quintal", "Narrativa", "Manifesto", "Projeto", "Ciclo", "Fusão", "Plano", "Lab", "Imersão", "Vertigem",
            "Vortex", "Circuito", "Paisagem", "Alquimia", "Espiral", "Câmara", "Panorama", "Universo", "Constelação",
            "Verbo", "Código", "Voz", "Cena Livre", "Trem de Histórias", "Caixa de Sonhos", "Pixel", "Trilho", "Traço",
            "Som", "Ritmo", "Batida", "Orquestra", "Sintonia", "Estrofe", "Metáfora", "Verso", "Luz", "Chama", "Fluxo",
            "Inspiração", "Reflexo", "Fábula", "Sopro", "Semente", "Raiz", "Galho", "Flor", "Alma", "Aura", "Essência",
            "dos Fundos", "Nerd"
    );

    private static final List<String> MALE_FIRST_NAME = Arrays.asList(
            "Abner", "Adalberto", "Adriano", "Agenor", "Ailton", "Airton", "Alcides", "Aldo", "Alef", "Alexandre",
            "Alfredo", "Aloísio", "Altair", "Aluísio", "Amadeu", "Amaro", "Amilcar", "Ananias", "Anderson", "Anselmo",
            "Antônio", "Aparecido", "Aquiles", "Arão", "Aristides", "Armando", "Arnaldo", "Arnoldo", "Artur", "Assis",
            "Ataíde", "Augusto", "Aureliano", "Aurelio", "Benedito", "Benjamim", "Bento", "Bernardo", "Bonifácio", "Bráulio",
            "Caetano", "Caio", "Camilo", "Carlinhos", "Cássio", "Cauê", "Celso", "Célio", "César", "Cícero",
            "Cláudio", "Clemente", "Conrado", "Cristiano", "Dagoberto", "Damião", "Danilo", "Dante", "Dario", "Demétrio",
            "Deoclécio", "Deraldo", "Diogo", "Djalma", "Domingos", "Dorival", "Douglas", "Edgar", "Edilson", "Edimilson",
            "Edmar", "Edmilson", "Edu", "Eduardo", "Elcio", "Eli", "Eliseu", "Elvis", "Emanoel", "Emanuel",
            "Emerson", "Enzo", "Eriberto", "Erick", "Ernani", "Ernesto", "Eron", "Eudes", "Evaldo", "Evandro",
            "Everton", "Ezequiel", "Ezio", "Fabrício", "Fausto", "Feliciano", "Felipe", "Fernando", "Flavio", "Florentino",
            "Francisco", "Franco", "Fred", "Frederico", "Gabriel", "Geraldo", "Genival", "Geovani", "Gerson", "Getúlio",
            "Gil", "Gilberto", "Gilmar", "Givanildo", "Guilherme", "Hamilton", "Haroldo", "Heber", "Heitor", "Henrique",
            "Hermes", "Hernani", "Hilário", "Humberto", "Iago", "Igor", "Ilton", "Inácio", "Irineu", "Ismael",
            "Israel", "Itamar", "Ivan", "Ivanildo", "Ivo", "Jacinto", "Jair", "Jaques", "Jean", "Jerônimo",
            "Joaquim", "Joel", "Jonas", "Jonathan", "Jorge", "José", "Josias", "Josué", "Juan", "Juliano",
            "Júlio", "Jurandir", "Juscelino", "Juvenal", "Kadu", "Kaique", "Kleber", "Laerte", "Lauro", "Leandro",
            "Lélio", "Leonardo", "Lindolfo", "Lineu", "Lívio", "Lourenço", "Lucas", "Luiz", "Luis", "Maciel",
            "Magno", "Maicon", "Maikon", "Manoel", "Marcelo", "Márcio", "Marco", "Marcos", "Marivaldo", "Mário",
            "Marlon", "Martinho", "Mateus", "Matheus", "Mauro", "Michel", "Milton", "Moacir", "Murilo", "Nabor",
            "Napoleão", "Natan", "Natanael", "Nelson", "Nélio", "Neuton", "Newton", "Nilson", "Nivaldo", "Noel",
            "Norberto", "Octávio", "Odair", "Odilon", "Olavo", "Onofre", "Orlando", "Osvaldo", "Otacílio", "Otávio",
            "Otoniel", "Paulo", "Pedro", "Plínio", "Quirino", "Rafael", "Raimundo", "Ramon", "Raul", "Reinaldo",
            "Renan", "Renato", "Ricardo", "Roberto", "Robson", "Rodolfo", "Rodrigo", "Rogério", "Rolando", "Ronaldo",
            "Roque", "Rubens", "Salatiel", "Samuel", "Sandro", "Saulo", "Sebastião", "Serafim", "Sérgio", "Severino",
            "Silas", "Silvano", "Silvério", "Sílvio", "Tadeu", "Tales", "Tarcísio", "Telmo", "Teodoro", "Thiago",
            "Tito", "Tomás", "Ubirajara", "Ulysses", "Uriel", "Valdemar", "Valdo", "Valmir", "Valter", "Vanderlei",
            "Vando", "Vicente", "Victor", "Vidal", "Vinicius", "Virgílio", "Vitor", "Vladimir", "Wagner", "Wallace",
            "Walter", "Washington", "Wellington", "Willian", "Wilson", "Wladimir", "Yago", "Yan", "Yuri", "Zacarias",
            "Zeca", "Zelito"
    );

    private static final List<String> FEMALE_FIRST_NAME = Arrays.asList(
            "Adriana", "Aída", "Aline", "Alzira", "Amália", "Amanda", "Amélia", "Ana", "Andreia", "Ângela",
            "Anita", "Antônia", "Aparecida", "Arlete", "Aurélia", "Aurora", "Beatriz", "Benedita", "Bruna", "Camila",
            "Carina", "Carla", "Carolina", "Cassia", "Catarina", "Cecília", "Clara", "Clarice", "Cláudia", "Conceição",
            "Cristiane", "Cristina", "Daiane", "Dalila", "Daniela", "Débora", "Denise", "Diana", "Diva", "Doraci",
            "Edileuza", "Elaine", "Eliane", "Elisa", "Elizabete", "Elza", "Emília", "Érica", "Estela", "Eva",
            "Evelyn", "Fabiana", "Fátima", "Fernanda", "Flávia", "Francisca", "Gabriela", "Geovana", "Gilda", "Gisele",
            "Gislene", "Graça", "Helena", "Iara", "Ilma", "Inês", "Iolanda", "Ione", "Iracema", "Irene",
            "Isabel", "Isabela", "Isadora", "Ivana", "Ivone", "Jacira", "Janaina", "Jandira", "Jaqueline", "Joana",
            "Joelma", "Josefa", "Josefina", "Jucélia", "Júlia", "Juliana", "Juraci", "Karen", "Karina", "Kátia",
            "Lara", "Larissa", "Laura", "Laurinda", "Léia", "Leila", "Lia", "Lilian", "Lígia", "Lívia",
            "Lorena", "Lucélia", "Lucia", "Luciana", "Luísa", "Lurdes", "Luzia", "Márcia", "Margarida", "Maria",
            "Mariana", "Marilene", "Marilza", "Marina", "Marlene", "Marta", "Matilde", "Melissa", "Mercedes", "Michele",
            "Milena", "Mirela", "Mônica", "Nádia", "Natália", "Neusa", "Nilce", "Nilza", "Nina", "Noemi",
            "Norma", "Olga", "Paloma", "Patrícia", "Paula", "Paulina", "Penélope", "Pietra", "Priscila", "Rafaela",
            "Raquel", "Regina", "Renata", "Rita", "Roberta", "Rosa", "Rosália", "Rosana", "Rosângela", "Rute",
            "Sandra", "Sara", "Selma", "Sheila", "Sílvia", "Simone", "Socorro", "Solange", "Sonia", "Soraia",
            "Stela", "Sueli", "Tânia", "Tatiane", "Teresa", "Thaís", "Tereza", "Valéria", "Vanda", "Vanessa",
            "Vera", "Verônica", "Violeta", "Vitória", "Vívian", "Wilma", "Yara", "Zenaide", "Zilda", "Zuleide"
    );

    private static final List<String> NON_BINARY_FIRST_NAME = Arrays.asList(
            "Alex", "Andréi", "Ariel", "Branco", "Cauã", "Cris", "Dani", "Davi", "Denis", "Dione",
            "Eli", "Eliá", "Eliete", "Elie", "Elis", "Emi", "Emerson", "Eron", "Estevão", "Felipe",
            "Fran", "Gabi", "Gabriel", "Gabrielle", "Gi", "Ira", "Isa", "Isaque", "Jô", "Jo",
            "Joan", "João", "Jordã", "Jules", "Julian", "Juli", "Lari", "Le", "Leo", "Leone",
            "Les", "Lírio", "Lou", "Lu", "Luan", "Luca", "Luc", "Manu", "Mar", "Marion",
            "Mica", "Micah", "Muriel", "Nico", "Nil", "Noa", "Noah", "Pietra", "Rafa", "Ray",
            "Renan", "Renata", "Renê", "Rian", "Riley", "Rô", "Roan", "Robin", "Sacha", "Sam",
            "Sami", "Sandro", "Sasha", "Sol", "Tali", "Tami", "Tay", "Theo", "Théo", "Tico",
            "Tony", "Val", "Vini", "Yago", "Yuri", "Zion", "Zuca",
            "Ale", "Alê", "Andy", "An", "Bê", "Babi", "Ben", "Biel", "Bia", "Bibi",
            "Caco", "Cris", "Cami", "Dani", "Duda", "Du", "Eli", "Emi", "Fê", "Fran",
            "Gabi", "Gale", "Gui", "Isa", "Iza", "Jo", "Jô", "Ju", "Kai", "Lari",
            "Le", "Leo", "Lipe", "Lu", "Lú", "Luna", "Manu", "Mari", "Max", "Mika",
            "Nalu", "Nico", "Noa", "Oz", "Pê", "Pipoca", "Rafa", "Ray", "Ren", "Rô",
            "Sam", "Sami", "Sol", "Tay", "Teo", "Theo", "Ti", "Tico", "Tuli", "Val",
            "Vic", "Vivi", "Will", "Zé", "Zuca", "Zizi"
    );


    private static final List<String> LAST_NAME = Arrays.asList(
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Almeida", "Costa", "Gomes", "Martins",
            "Araújo", "Barbosa", "Pereira", "Lima", "Carvalho", "Rocha", "Ribeiro", "Alves", "Monteiro", "Moura",
            "Dias", "Nascimento", "Cavalcante", "Campos", "Castro", "Teixeira", "Freitas", "Andrade", "Vieira", "Cunha",
            "Machado", "Correia", "Moreira", "Cardoso", "Soares", "Batista", "Reis", "Farias", "Pinto", "Sales",
            "Melo", "Mendes", "Ramos", "Torres", "Barros", "Jesus", "Bezerra", "Xavier", "Leal", "Tavares",
            "Borges", "Brandão", "Rezende", "Fonseca", "Assis", "Neves", "Guimarães", "Figueiredo", "Peixoto", "Amaral",
            "Antunes", "Marques", "Aguiar", "Chaves", "Viana", "Mattos", "Nogueira", "Queiroz", "Lopes", "Simões",
            "Duarte", "Bittencourt", "Azevedo", "Gonçalves", "Navarro", "Pacheco", "Drummond", "Serpa", "Valente", "Camargo",
            "Rangel", "Severino", "Pinheiro", "Alencar", "Travassos", "Teles", "Leite", "Galvão", "Siqueira", "Meireles",
            "Torrado", "Fernandes", "Castilho", "Frota", "Gadelha", "Sarmento", "Marinho", "Benevides", "Carmo", "Bicalho",
            "Tenório", "Menezes", "Muniz", "Vasconcelos", "Cordeiro", "Prado", "Gurgel", "Lacerda", "Ayres", "Jardim",
            "Tavajara", "Lobo", "Buarque", "Capanema", "Cavalcanti", "Rebouças", "Barreto", "Barbalho", "Brant",
            "Bourbon", "Pedrosa", "Pontes", "Seixas", "Taunay", "Andrada", "Alencastro", "Araripe", "Bulhões",
            "Itamaraty", "Leão", "Lessa", "Munhoz", "Negrão", "Paes", "Pernambuco", "Pombal", "Rolim",
            "Sá", "Saldanha da Gama", "Tamandaré", "Vilhena", "Vidigal", "Visconde", "Magalhães", "Marcondes", "Cândido", "Osório",
            "Marte", "Baldino"
    );

    public String generateCrewName(String gender){
        String fname;
        String lname;
        switch (gender.toUpperCase()){
            case "MALE" ->  fname = MALE_FIRST_NAME.get(random.nextInt(MALE_FIRST_NAME.size()));
            case "FEMALE" -> fname = FEMALE_FIRST_NAME.get(random.nextInt(FEMALE_FIRST_NAME.size()));
            case "NON_BINARY" -> fname = NON_BINARY_FIRST_NAME.get(random.nextInt(NON_BINARY_FIRST_NAME.size()));
            default -> throw new RuntimeException();
        }
        lname = LAST_NAME.get(random.nextInt(LAST_NAME.size()));
        return (fname + " " + lname);
    }

    public String generateStudioName() {
        String prefix = STUDIO_PREFIXES.get(random.nextInt(STUDIO_PREFIXES.size()));
        String suffix = STUDIO_SUFFIXES.get(random.nextInt(STUDIO_SUFFIXES.size()));
        return prefix + " " + suffix;
    }

}
