ps. private final static int TEST_NUM = 50000;

# 1 
ThreadNum: 4 BuyAvgTime(ns): 14411 RefundAvgTime(ns): 842 InquiryAvgTime(ns): 20852 ThroughOut(t/s): 226000
ThreadNum: 8 BuyAvgTime(ns): 18045 RefundAvgTime(ns): 402 InquiryAvgTime(ns): 25609 ThroughOut(t/s): 373000
ThreadNum: 16 BuyAvgTime(ns): 31345 RefundAvgTime(ns): 479 InquiryAvgTime(ns): 40629 ThroughOut(t/s): 454000
ThreadNum: 32 BuyAvgTime(ns): 121051 RefundAvgTime(ns): 1040 InquiryAvgTime(ns): 155197 ThroughOut(t/s): 238000
ThreadNum: 64 BuyAvgTime(ns): 280517 RefundAvgTime(ns): 1183 InquiryAvgTime(ns): 351688 ThroughOut(t/s): 210000

# use ReadWriteLock     fail
ThreadNum: 4 BuyAvgTime(ns): 16859 RefundAvgTime(ns): 901 InquiryAvgTime(ns): 29828 ThroughOut(t/s): 166000
ThreadNum: 8 BuyAvgTime(ns): 22771 RefundAvgTime(ns): 456 InquiryAvgTime(ns): 39021 ThroughOut(t/s): 258000
ThreadNum: 16 BuyAvgTime(ns): 29510 RefundAvgTime(ns): 604 InquiryAvgTime(ns): 54459 ThroughOut(t/s): 364000
ThreadNum: 32 BuyAvgTime(ns): 90420 RefundAvgTime(ns): 1327 InquiryAvgTime(ns): 203855 ThroughOut(t/s): 203000
ThreadNum: 64 BuyAvgTime(ns): 162722 RefundAvgTime(ns): 1766 InquiryAvgTime(ns): 512212 ThroughOut(t/s): 173000

# use StampedLock       success
ThreadNum: 4 BuyAvgTime(ns): 17926 RefundAvgTime(ns): 920 InquiryAvgTime(ns): 10439 ThroughOut(t/s): 315000
ThreadNum: 8 BuyAvgTime(ns): 21863 RefundAvgTime(ns): 479 InquiryAvgTime(ns): 10099 ThroughOut(t/s): 606000
ThreadNum: 16 BuyAvgTime(ns): 32913 RefundAvgTime(ns): 675 InquiryAvgTime(ns): 13983 ThroughOut(t/s): 800000
ThreadNum: 32 BuyAvgTime(ns): 82058 RefundAvgTime(ns): 447 InquiryAvgTime(ns): 14960 ThroughOut(t/s): 855000
ThreadNum: 64 BuyAvgTime(ns): 365025 RefundAvgTime(ns): 2327 InquiryAvgTime(ns): 28540 ThroughOut(t/s): 457000