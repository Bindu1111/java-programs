import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SortingBigDecimals {

	public static final Comparator<BigDecimal> comparator = new Comparator<BigDecimal>(){
        @Override
        public int compare(BigDecimal b1, BigDecimal b2) {
            return b2.compareTo(b1);
        }
    };

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		List<BigDecimal> list = new ArrayList<>();
		
		for(int i = 0; i < n; ++ i)
			list.add(new BigDecimal(scanner.nextLine()));
		scanner.close();
		
		Collections.sort(list, comparator);
		Iterator<BigDecimal> iterator = list.iterator();
		
		BigDecimal bigDecimal = null;
		String string = "";
		while(iterator.hasNext())
		{
			bigDecimal = iterator.next();
			string = bigDecimal.toPlainString();
			
			if(string.length() > 1 && string.startsWith("0"))
				System.out.println(string.substring(1));
			
			else
				System.out.println(bigDecimal);
		}
	}
}