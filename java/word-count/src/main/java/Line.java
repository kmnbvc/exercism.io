import java.util.*;

public class Line {

    private static final Integer BILL_COST = 25;

    public static String Tickets(int[] peopleInLine) {
        CashDesk cashDesk = new CashDesk();
        for (int income : peopleInLine) {
            cashDesk.income(income);
            if (!cashDesk.change(income - BILL_COST))
                return "NO";
        }
        return "YES";
    }


    private static class CashDesk {
        private TreeMap<Integer, Integer> cash = new TreeMap<>(Comparator.reverseOrder());

        public void income(int income) {
            cash.put(income, cash.getOrDefault(income, 0) + 1);
        }

        public boolean change(int change) {
            for (Map.Entry<Integer, Integer> entry : cash.entrySet()) {
                Integer nominal = entry.getKey();
                Integer num = entry.getValue();
                while (change >= nominal && num > 0 && change > 0) {
                    change -= nominal;
                    num--;
                }
                cash.put(nominal, num);
            }

            return change == 0;
        }
    }
}
