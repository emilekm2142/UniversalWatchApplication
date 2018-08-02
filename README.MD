# Flow
## From watch to phone
Bluetooth module hands eveything to the receiverModule which then sends it to the target app. Receiver module seems redudant, but I quickly lost myself wiythout it.

## From phone to watch
Application send the JSON object as string through broadcasts to the senderModule. Next, it send almost the same data to the bt module. Again - sender seems redudant but helps to maintain things.
Also, in bt module takes place all the processing of image, because broadcasts have limit storage and images would not fit. There is a class for that! WatchDataPreProcessor 