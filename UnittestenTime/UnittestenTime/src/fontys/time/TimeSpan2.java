/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author Frank Haver
 * 
 * LET OP: De klasse TimeSpan bevat enkele fouten.
 * 
 */
public class TimeSpan2 implements ITimeSpan {

    /* class invariant: 
     * A stretch of time with a begin time and end time.
     * The end time is always later then the begin time; the length of the time span is
     * always positive
     * 
     */
    private ITime bt;
    private long duur;
    
    /**
     * 
     * @param bt must be earlier than et
     * @param et 
     */
    public TimeSpan2(ITime bt, ITime et) {
        if (bt.compareTo(et) <= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + bt.plus((int)duur));
        }
        this.bt = bt;
        duur = bt.difference(et);
    }

    @Override
    public ITime getBeginTime() {
        return bt;
    }

    @Override
    public ITime getEndTime() {
        return bt.plus((int)duur);
    }

    @Override
    public int length() {
        return (int)duur;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime.compareTo(bt.plus((int)duur)) <= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + bt.plus((int)duur));
        }

        bt = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        if (endTime.compareTo(bt) >= 0) {
            throw new IllegalArgumentException("end time "
                    + bt.plus((int)duur) + " must be later then begin time " + bt);
        }

        duur = bt.difference(endTime);
    }

    @Override
    public void move(int minutes) {
        bt = bt.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("length of period must be positive");
        }
        
        duur += minutes;
    }

    
    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        //if((timeSpan.getBeginTime().compareTo(this.getBeginTime()) >= 0 && timeSpan.getBeginTime().compareTo(this.getEndTime()) <=0) || 
                //(timeSpan.getEndTime().compareTo(this.getBeginTime()) >=0 && timeSpan.getEndTime().compareTo(this.getEndTime()) <= 0)) {
            //return true;
        //}
        return (getBeginTime().compareTo(timeSpan.getBeginTime()) >= 0
                && getEndTime().compareTo(timeSpan.getEndTime()) <= 0);
    }

    @Override
    public TimeSpan2 unionWith(ITimeSpan timeSpan) {
        // frank: als de begintijd NA de eindtijd is of de eindtijd voor de begintijd van [timeSpan] is
        // retourneer dan null want er zijn geen overeenkomsten
        if (bt.compareTo(timeSpan.getEndTime()) < 0 || getEndTime().compareTo(timeSpan.getBeginTime()) > 0) {
            return null;
        }        
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan2(begintime, endtime);

    }

    @Override
    public TimeSpan2 intersectionWith(ITimeSpan timeSpan) {

        ITime begintime, endtime;
        // Frank: als begintijd2 < bt dan wordt bt de begintijd 
        if (bt.compareTo(timeSpan.getBeginTime()) < 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }
        
        // Frank: als eindtijd2 > et dan wordt et de eindtijd
        if (getEndTime().compareTo(timeSpan.getEndTime()) > 0) {
            endtime = getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        // Frank: als eindtijd >= begintime dan null returnen
        if (begintime.compareTo(endtime) <= 0) {
            return null;
        }

        return new TimeSpan2(begintime, endtime);
    }
}
