import rabin_karp.RabinKarpExtended;

public class Main {
    public static void main(String[] args) {


        RabinKarpExtended rabinKarpExtended = new RabinKarpExtended("It's a good approach to place a query definition just above the method inside the repository rather than inside our domain model as named queries. The repository is responsible for persistence, so it's a better place to store these definitions");
        rabinKarpExtended.search("approach");
    }
}
