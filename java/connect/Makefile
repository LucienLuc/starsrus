JFLAGS = -d classes
JC = javac

SQLPATH = .:sqlite-jdbc-3.34.0.jar

.SUFFIXES: .java .class

CLASSES = \
	net/starsrus/Main.java \
	net/starsrus/Customer.java \
	net/starsrus/Actor.java \
	net/starsrus/Helper.java \
	net/starsrus/ui/UserInterface.java \
	net/starsrus/ui/Login.java \
	net/starsrus/ui/Register.java \
	net/starsrus/ui/LogReg.java \
	net/starsrus/ui/TraderInterface.java \
	net/starsrus/ui/DepWith.java \
	net/starsrus/ui/Buy.java \
	net/starsrus/Stock.java \
	net/starsrus/MarketAccount.java \
	net/starsrus/StockAccount.java

.PHONY: classes

classes:
	$(JC) $(JFLAGS) $(CLASSES)

run: 
	java -cp "$(SQLPATH)":classes starsrus.Main run

setup:
	java -cp "$(SQLPATH)":classes starsrus.Main setup

clean:
	rm -r ./classes/*
