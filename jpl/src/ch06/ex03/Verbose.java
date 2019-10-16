package ch06.ex03;

// 6.3
// 104頁4.2.1節のVerboseインタフェースを、整数定数の代わりに、
// メッセージレベルのためのenumを使用して再定義しなさい。

// まだ直してないよ

interface Verbose {
	enum MESSAGE_LEVEL {
		SILENT,
		TERSE,
		NORMAL,
		VERBOSE,
	}

	void setVerbosity(MESSAGE_LEVEL level);
	MESSAGE_LEVEL getVerbosity();
}
