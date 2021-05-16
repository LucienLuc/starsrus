run: 
	java -cp ".:sqlite-jdbc-3.34.0.jar":classes source.Main

classes:
	javac -d classes net/source/Main.java net/source/Customer.java net/source/Account.java net/source/MarketAccount.java net/source/StockAccount.java

clean:
	rm -r ./classes/*
