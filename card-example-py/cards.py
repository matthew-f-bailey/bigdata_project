from mrjob.job import MRJob
import json
import happybase

class MRWordCount(MRJob):

    def mapper_init(self):
        # Connect to HBase
        self.connection = happybase.Connection('hbase-docker', port=9090)  # Replace with your HBase container name
        self.connection.open()
        self.table = self.connection.table('cards')  # Replace with your HBase table name

    def mapper_final(self):
        # Close the connection to HBase
        self.connection.close()

    def mapper(self, _, line):
        suits = ["Hearts", "Spades", "Clubs", "Diamonds"]
        vals = list(range(2, 11)) + ["J", "Q", "K", "A"]

        # Construct the full deck
        full_deck = set(f"{value}:{suit}" for suit in suits for value in vals)
        deck = set([str(card["value"])+":"+card["suit"] for card in json.loads(line)])
        missing = full_deck.difference(deck)
        for card in missing:
            yield card, 1

    def reducer(self, card, counts):
        total_count = sum(counts)
        if total_count>0:
            yield card, total_count

if __name__ == '__main__':
    MRWordCount.run()