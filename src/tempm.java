public class tempm {
    public static void main(String[] args) {
        Authenticator aut = new Authenticator();

        User user7 = new User("grace.wilson@example.com", 0, "jkl", "Wilson", "Editor", "Grace", "editPass");
        int response = aut.createUseAccount(user7);
        System.out.println(response);
    }
}
