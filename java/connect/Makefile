JFLAGS = -d classes
JC = javac

SQLPATH = .:sqlite-jdbc-3.34.0.jar

.SUFFIXES: .java .class

CLASSES = \
	net/starsrus/Main.java \
	\
	net/starsrus/sql/Sys.java \
	net/starsrus/sql/Manager.java \
	net/starsrus/sql/Customer.java \
	net/starsrus/sql/Actor.java \
	net/starsrus/sql/Transaction.java \
	net/starsrus/sql/Stock.java \
	net/starsrus/sql/MarketAccount.java \
	net/starsrus/sql/StockAccount.java \
	net/starsrus/sql/Contracts.java \
	net/starsrus/sql/Movie.java \
	net/starsrus/sql/Helper.java \
	net/starsrus/sql/Debug.java \
	\
	net/starsrus/ui/UserInterface.java \
	net/starsrus/ui/pub/Login.java \
	net/starsrus/ui//pub/Register.java \
	net/starsrus/ui/pub/LogReg.java \
	net/starsrus/ui/pub/ManLogin.java \
	net/starsrus/ui/ti/TraderInterface.java \
	net/starsrus/ui/ti/DepWith.java \
	net/starsrus/ui/ti/TransHistory.java \
	net/starsrus/ui/ti/Buy.java \
	net/starsrus/ui/ti/Sell.java \
	net/starsrus/ui/ti/StockInfo.java \
	net/starsrus/ui/ti/MovieInfo.java \
	net/starsrus/ui/ti/TopMovies.java \
	net/starsrus/ui/ti/Reviews.java \
	\
	net/starsrus/ui/mi/ManagerInterface.java \
	net/starsrus/ui/mi/GenStmt.java \
	net/starsrus/ui/mi/CustRep.java \
	net/starsrus/ui/mi/GenDter.java \
	net/starsrus/ui/mi/ListActive.java \
	net/starsrus/ui/mi/AddInterest.java \
	net/starsrus/ui/mi/SetStockPrice.java \
	net/starsrus/ui/mi/SetDate.java \

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
