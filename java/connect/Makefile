run: 
	java -cp ".:sqlite-jdbc-3.34.0.jar":classes starsrus.Main

classes:
	javac -d classes net/starsrus/Main.java net/starsrus/Customer.java net/starsrus/Account.java net/starsrus/MarketAccount.java net/starsrus/StockAccount.java

clean:
	rm -r ./classes/*
