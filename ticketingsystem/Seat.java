package ticketingsystem;

import java.util.concurrent.locks.StampedLock;

/**
 * 所有的加锁都是在seat对象上完成，粒度大小就是seat
 * 一个seat对象还记录了各个区间的票
 */
class Seat {
    private volatile boolean[] selled;
//    // 尝试使用StampedLock优化
//    final StampedLock stampedLock = new StampedLock();

    public Seat(int stationnum){
        selled = new boolean[stationnum-1];
    }

    // 尝试购票，成功了就返回true并修改相应区间的值
    public synchronized boolean tryBuy(int departure, int arrival){
        for (int i = departure-1; i < arrival-1; i++){
            if (selled[i]){
                return false;
            }
        }
        for (int i = departure-1; i < arrival-1; i++){
            selled[i] = true;
        }
        return true;
    }

    // 查询是否可以购票，用于查询剩余票数
    public boolean canSelled(int departure, int arrival){
//        long stamp = stampedLock.tryOptimisticRead();
        for (int i = departure-1; i < arrival-1; i++){
            if (selled[i]){
                return false;
            }
        }
//        if (!stampedLock.validate(stamp)){
//            stamp = stampedLock.readLock();
//            try {
//                for (int i = departure-1; i < arrival-1; i++){
//                    if (selled[i]){
//                        return false;
//                    }
//                }
//            } finally {
//                stampedLock.unlockRead(stamp);
//            }
//        }
        return true;
    }

    // 退票，因为前面会加前置的判断，所以此操作一旦执行，必成功
    public synchronized void free(int departure, int arrival){
        for (int i = departure-1; i < arrival-1; i++){
            selled[i] = false;
        }
    }
}
