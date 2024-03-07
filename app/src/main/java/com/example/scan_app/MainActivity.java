package com.example.scan_app;
import com.zebra.rfid.api3.*;

 class RFIDScanner {

    private RFIDReader rfidReader;

    public RFIDScanner() {
        // Initialize RFID Reader
        rfidReader = new RFIDReader();
    }

    public void startScanning() {
        try {
            // Establish connection to the RFID reader
            rfidReader.connect();

            // Register event handler for tag reads
            rfidReader.Events.addEventsListener(new EventsHandler());

            // Enable tag read events
            rfidReader.Events.setTagReadEvent(true);

            // Start inventory operation
            rfidReader.Actions.Inventory.perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopScanning() {
        try {
            // Stop inventory operation
            rfidReader.Actions.Inventory.stop();
            // Disconnect from the RFID reader
            rfidReader.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class EventsHandler implements RfidEventsListener {
        @Override
        public void eventReadNotify(RfidReadEvents rfidReadEvents) {
            // Specify the maximum number of tags to read
            int maxTags = 1000000;
            TagData[] tagDataArray = rfidReader.Actions.getReadTags(maxTags);
            if (tagDataArray != null) {
                for (TagData tagData : tagDataArray) {
                    String tagID = tagData.getTagID();
                    System.out.println("RFID Tag ID: " + tagID);
                }
            }
        }

        @Override
        public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {
            // Gérer les événements de statut du lecteur RFID si nécessaire
        }
    }


    public static void main(String[] args) {
        RFIDScanner scanner = new RFIDScanner();
        scanner.startScanning();

        // Keep scanning for a while
        try {
            Thread.sleep(5000); // Scan for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.stopScanning();
    }
}
