package util;

import java.util.HashMap;
import java.util.Map;

public class Validador {

    private static final Map<String, String> DDD_ESTADOS = new HashMap<>();

    static {
        DDD_ESTADOS.put("68", "AC");
        DDD_ESTADOS.put("82", "AL");
        DDD_ESTADOS.put("96", "AP");
        DDD_ESTADOS.put("92", "AM"); DDD_ESTADOS.put("97", "AM");
        DDD_ESTADOS.put("71", "BA"); DDD_ESTADOS.put("73", "BA"); DDD_ESTADOS.put("74", "BA"); DDD_ESTADOS.put("75", "BA"); DDD_ESTADOS.put("77", "BA");
        DDD_ESTADOS.put("85", "CE"); DDD_ESTADOS.put("88", "CE");
        DDD_ESTADOS.put("61", "DF");
        DDD_ESTADOS.put("27", "ES"); DDD_ESTADOS.put("28", "ES");
        DDD_ESTADOS.put("62", "GO"); DDD_ESTADOS.put("64", "GO");
        DDD_ESTADOS.put("98", "MA"); DDD_ESTADOS.put("99", "MA");
        DDD_ESTADOS.put("65", "MT"); DDD_ESTADOS.put("66", "MT");
        DDD_ESTADOS.put("67", "MS");
        DDD_ESTADOS.put("31", "MG"); DDD_ESTADOS.put("32", "MG"); DDD_ESTADOS.put("33", "MG"); DDD_ESTADOS.put("34", "MG"); DDD_ESTADOS.put("35", "MG"); DDD_ESTADOS.put("37", "MG"); DDD_ESTADOS.put("38", "MG");
        DDD_ESTADOS.put("91", "PA"); DDD_ESTADOS.put("93", "PA"); DDD_ESTADOS.put("94", "PA");
        DDD_ESTADOS.put("83", "PB");
        DDD_ESTADOS.put("41", "PR"); DDD_ESTADOS.put("42", "PR"); DDD_ESTADOS.put("43", "PR"); DDD_ESTADOS.put("44", "PR"); DDD_ESTADOS.put("45", "PR"); DDD_ESTADOS.put("46", "PR");
        DDD_ESTADOS.put("81", "PE"); DDD_ESTADOS.put("87", "PE");
        DDD_ESTADOS.put("86", "PI"); DDD_ESTADOS.put("89", "PI");
        DDD_ESTADOS.put("21", "RJ"); DDD_ESTADOS.put("22", "RJ"); DDD_ESTADOS.put("24", "RJ");
        DDD_ESTADOS.put("84", "RN");
        DDD_ESTADOS.put("51", "RS"); DDD_ESTADOS.put("53", "RS"); DDD_ESTADOS.put("54", "RS"); DDD_ESTADOS.put("55", "RS");
        DDD_ESTADOS.put("69", "RO");
        DDD_ESTADOS.put("95", "RR");
        DDD_ESTADOS.put("47", "SC"); DDD_ESTADOS.put("48", "SC"); DDD_ESTADOS.put("49", "SC");
        DDD_ESTADOS.put("11", "SP"); DDD_ESTADOS.put("12", "SP"); DDD_ESTADOS.put("13", "SP"); DDD_ESTADOS.put("14", "SP"); DDD_ESTADOS.put("15", "SP"); DDD_ESTADOS.put("16", "SP"); DDD_ESTADOS.put("17", "SP"); DDD_ESTADOS.put("18", "SP"); DDD_ESTADOS.put("19", "SP");
        DDD_ESTADOS.put("79", "SE");
        DDD_ESTADOS.put("63", "TO");
    }

    public static String formatarNome(String texto) {
        if (texto == null || texto.trim().isEmpty()) return texto;
        String[] palavras = texto.split(" ");
        StringBuilder resultado = new StringBuilder();
        for (String palavra : palavras) {
            if (palavra.length() > 0) {
                resultado.append(Character.toUpperCase(palavra.charAt(0)))
                         .append(palavra.substring(1).toLowerCase())
                         .append(" ");
            }
        }
        return resultado.toString().trim();
    }

    public static boolean isEmailValido(String email) {
        String em = email.toLowerCase();
        return em.contains("@gmail.com") || em.contains("@hotmail.com") || em.contains("@yahoo.com");
    }

    public static boolean isNomeValido(String nome) {
        if (nome == null || nome.trim().isEmpty()) return false;
        return nome.matches("^[a-zA-ZÀ-ÿ\\s]+$");
    }

    public static boolean isTelefoneValido(String telefone) {
        if (!telefone.matches("^\\(\\d{2}\\)\\s?\\d{8,9}$")) return false;
        
        String ddd = telefone.substring(1, 3);
        
        return DDD_ESTADOS.containsKey(ddd);
    }

    public static String obterEstadoPorTelefone(String telefone) {
        if (telefone != null && telefone.length() >= 3) {
            String ddd = telefone.substring(1, 3);
            return DDD_ESTADOS.getOrDefault(ddd, "Desconhecido");
        }
        return "Desconhecido";
    }
}