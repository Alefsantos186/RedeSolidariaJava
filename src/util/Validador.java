package util;

public class Validador {

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
        return telefone.matches("^\\(\\d{2}\\)\\s?\\d{8,9}$"); 
    }
}