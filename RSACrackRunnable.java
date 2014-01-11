import java.math.BigInteger;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * @author Roseline Okpara
 * 
 * RSACrackRunnable creates the runnable object for every pair of RSA keys
 *
 */
public class RSACrackRunnable implements Runnable{
    
    /**
     * Private members
     */
    private CyclicBarrier barrier;
    private BigInteger modulus1;
    private BigInteger modulus2;  
    private static TreeMap<BigInteger,LinkedList<BigInteger>> RESULTS 
        = new TreeMap<BigInteger,LinkedList<BigInteger>>();
    
    /**
     * Constructor for the Runnable object
     * @param n1
     * @param n2
     * @param theBarrier
     */
    public RSACrackRunnable(BigInteger n1, BigInteger n2, CyclicBarrier theBarrier){    
        barrier = theBarrier;
        modulus1 = n1;
        modulus2 = n2;    
    }
    
    /**
     * Calculates the gcd of the keys and determines if if they have a common denominator
     * to figure out the factors. If it has factors, it is inserted into the results map.
     */
    public synchronized void calculateResult(){
        BigInteger gcd1 = modulus1.gcd(modulus2);
        if (!gcd1.equals(BigInteger.ONE)){
            LinkedList<BigInteger> mod1 = new LinkedList<BigInteger>();
            LinkedList<BigInteger> mod2 = new LinkedList<BigInteger>();
            BigInteger factor1 = modulus1.divide(gcd1);
            mod1.add(factor1);
            mod1.add(gcd1);
            BigInteger factor2 = modulus2.divide(gcd1);
            mod2.add(factor2);
            mod2.add(gcd1);
            insertToResult(modulus1, mod1);
            insertToResult(modulus2, mod2);   
        }
    }
    
    /**
     * Inserts a key and its factors into the result table
     * 
     * @param mod
     * @param factors
     */
    public synchronized void insertToResult(BigInteger mod, LinkedList<BigInteger> factors){
            RESULTS.put(mod,factors);    
    }
    
    /**
     * Prints the contents of the map which contains the RSA keys and it's factors
     */
    public static void print(){
        for(Entry<BigInteger, LinkedList<BigInteger>> entry : RESULTS.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().get(0) + " " + entry.getValue().get(1));
        }
        
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            calculateResult();
            barrier.await();            
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
}

