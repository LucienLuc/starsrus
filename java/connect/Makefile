run: 
	java -cp ".:sqlite-jdbc-3.34.0.jar":classes starsrus.Main

classes:
	javac -d classes net/starsrus/Main.java net/starsrus/Customer.java net/starsrus/Actor.java net/starsrus/Helper.java net/starsrus/UserInterface.java net/starsrus/Login.java net/starsrus/Register.java net/starsrus/LogReg.java net/starsrus/TraderInterface.java net/starsrus/MarketAccount.java net/starsrus/StockAccount.java

clean:
	rm -r ./classes/*
