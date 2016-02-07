PathName=/home/ubuntu/Programming/Kalah-Development/Releases/Kalah/TA

cramTournament:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.CramTournamentSimulator

cramDistributedTournamentPlayer1:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.CramTournamentSimulator 1

cramDistributedTournamentPlayer2:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.CramTournamentSimulator 2

kalahTournament:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.KalahTournamentSimulator

kalahDistributedTournamentPlayer1:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.KalahTournamentSimulator 1

kalahDistributedTournamentPlayer2:
	cd $(PathName)/classes/algorithms; \
	rm -f *
	cd $(PathName)/source; \
	javac -Xlint:unchecked -d $(PathName)/classes algorithms/*.java; \
	cd $(PathName)/classes; \
	java -Xms2048m -Xmx16384m org.gamelink.main.KalahTournamentSimulator 2