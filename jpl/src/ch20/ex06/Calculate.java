package ch20.ex06;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.HashMap;

// 20.6
// name op value 形式の入力を受け取るプログラムを作成しなさい。
// nameは自分で選んだ3個の単語の1つで、opは+,-,=のどれかで、valueは数です。
// 名前付値に各演算子を適用しなさい。入力がなくなったら、3つの値を表示しなさい。
// もし、興味があれば、AttributedImplで使用されたHashMapを使用してみなさい。
// そううすれば、任意の個数の名前付定数を使用できます。

public class Calculate {
	public static void main(String[] args) throws IOException {
		int b;
		InputStreamReader isr = new InputStreamReader(System.in);
		System.out.println(sumStream(isr));
		isr.close();
	}
	static HashMap<String, Double> sumStream(Reader source) throws IOException {
		HashMap<String, Double> map = new HashMap<>();
		StreamTokenizer in = new StreamTokenizer(source);
		String name;
		char op;
		double preVal;
		name = "";
		op = ' ';
		preVal = 0;
		while (in.nextToken() != StreamTokenizer.TT_EOL) {
			if (in.ttype == StreamTokenizer.TT_WORD) {
				if (in.sval.equals("end")) break;
				if (map.get(in.sval) == null) {
					name = in.sval;
					preVal = 0;
				} else {
					name = in.sval;
					preVal = map.get(name);
				}
			} else if (in.ttype == StreamTokenizer.TT_NUMBER) {
				if (name == "") {
					throw new IOException("3rd-token needs number");
				}
				if (map.size() == 3 && !map.containsKey(name)) {
					System.out.println("over three value");
					continue;
				}
				if (op == '=') {
					map.put(name, in.nval);
					name = "";
				} else if (op == '+') {
					map.put(name, preVal+in.nval);
					name = "";
				} else if (op == '-') {
					map.put(name, preVal-in.nval);
					name = "";
				}
			} else {
				op = (char)in.ttype;
			}
		}
		return map;
	}

}
