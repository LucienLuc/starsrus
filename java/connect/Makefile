JFLAGS = -d classes
JC = javac

SQLPATH = .:sqlite-jdbc-3.34.0.jar

.SUFFIXES: .java .class

CLASSES = \
	net/starsrus/Main.java \
	net/starsrus/Sys.java \
	net/starsrus/Manager.java \
	net/starsrus/Customer.java \
	net/starsrus/Actor.java \
	net/starsrus/Transaction.java \
	net/starsrus/Stock.java \
	net/starsrus/MarketAccount.java \
	net/starsrus/StockAccount.java \
	net/starsrus/Contracts.java \
	net/starsrus/Movie.java \
	net/starsrus/Helper.java \
	net/starsrus/Debug.java \
	\
	net/starsrus/ui/UserInterface.java \
	net/starsrus/ui/Login.java \
	net/starsrus/ui/Register.java \
	net/starsrus/ui/LogReg.java \
	net/starsrus/ui/ManLogin.java \
	net/starsrus/ui/TraderInterface.java \
	net/starsrus/ui/DepWith.java \
	net/starsrus/ui/TransHistory.java \
	net/starsrus/ui/Buy.java \
	net/starsrus/ui/Sell.java \
	net/starsrus/ui/StockInfo.java \
	net/starsrus/ui/MovieInfo.java \
	net/starsrus/ui/TopMovies.java \
	net/starsrus/ui/Reviews.java \
	\
	net/starsrus/ui/ManagerInterface.java \
	net/starsrus/ui/GenStmt.java \
	net/starsrus/ui/CustRep.java \
	net/starsrus/ui/GenDter.java \
	net/starsrus/ui/ListActive.java \
	net/starsrus/ui/AddInterest.java \
	net/starsrus/ui/SetStockPrice.java \
	net/starsrus/ui/SetDate.java \

.PHONY: classes

classes:
	$(JC) $(JFLAGS) $(CLASSES)

run: 
	java -cp "$(SQLPATH)":classes starsrus.Main run

setup:
	java -cp "$(SQLPATH)":classes starsrus.Main setup

reset:
	java -cp "$(SQLPATH)":classes starsrus.Main reset

clean:
	rm -r ./classes/*
