public class HelloWorld {

    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : null;
        System.out.println(hello(name));
    }

    public static String hello(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello, World!";
        } else {
            return "Hello, " + name + "!";
        }
    }

}
