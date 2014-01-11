RSA-Public-Key-Cracking
=======================

RSA is the most popular public key cryptosystem in the world. It is used by many, many web sites to set up secure (https://) connections. If anyone cracks a secure web site's RSA public key, security on that web site is destroyed, and secure e-commerce on that web site becomes impossible. Here is an explanation of the aspects of RSA needed to do this project.

An RSA public key consists of an exponent e and a modulus n. For this project we are only concerned with the modulus. An RSA modulus is the product of two secret prime numbers p and q: n = pq. For security, p and q are large prime numbers, typically at least 1024 bits in binary; then n is a 2048-bit binary number. Here is an example:

p = 131504983344666497997247391227148477187915980511410059153836658241896376670646449788350139815770084444640067092249971284044679154763860072283532106228154491493367366128862255154194345174844062325151089826329014721466014229328808477175986065559794374349346019013822816401505201773123511634014150881884280222199

q = 170245394201671489160339054390478038203645926235216355420353921703700353830805120706390674907934127819174341661084155376655408909357990565227256275939588716046778366293485012051209360100097076072275310280959158660203029609664278271759867465267588579646633719784702645993243077686872322430924369441276297242751

n = 22388117728996991573266070571577825430301386113027161337459043126449048908415228998507558449856779151445304433725318912994261324170148188781331203814761116857774682433277397592707425208397747471669951098693581044485791678719131646146233189901461026354979683435039656066495102434288959201375691143874027872750089414633756790430523886905678415587248852600608589995154234481041078367109794938826903417447744118225141086160785883392168834038011994467341446256182233246944523249038665678383414791268158887147073059163997289161036147899525530899576857045226756191639136368576638195655236811969381274032567121602825322029449

To crack an RSA modulus n, you find its two factors p and q. However, the fastest known integer factoring algorithms simply are not practical when n is this large; the universe will have ended before the algorithm finds the factors. The security of RSA rests on the difficulty of factoring large integers.

However, there is another way to crack RSA moduli. Suppose we have two RSA moduli: n1 = p1q1 and n2 = p2q2. p1, q1, p2, and q2 are secret. But suppose p1 = p2; that is, n1 and n2 have a common factor. Then computing the greatest common denominator (GCD) of n1 and n2 yields that common factor. Unlike finding the factors of an integer, there is a very simple algorithm for finding the GCD of two integers, namely the Euclidean Algorithm. This algorithm runs quickly, even on 2048-bit numbers.

If GCD(n1,n2) = 1, then we learn nothing about the factors of n1 and n2 (except the trivial fact that both are divisible by 1). Otherwise, GCD(n1,n2) = p1, the common factor. We can then easily find the other factor q1 = n1/p1. Likewise, q2 = n2/p1.

No two RSA moduli are ever supposed to have a common factor. However, it turns out that in the real world, about two out of 1000 secure web sites' RSA moduli do have common factors, and thus are susceptible to cracking. This was published recently:

A. Lenstra, J. Hughes, M. Augier, J. Bos, T. Kleinjung, and C. Wachter. Ron was wrong, Whit is right. Cryptology ePrint Archive, Report 2012/064, February 17, 2012. http://eprint.iacr.org/2012/064
Programming Project 2 is multithreaded Java program that cracks RSA moduli. To write this program, you will need to work with class java.math.BigInteger. Refer to the Javadoc for a complete list of class BigInteger's constructors and methods. Here are some you may find useful.

To convert a String to a BigInteger: 
  - String s = ...; 
  - BigInteger n = new BigInteger (s);

To read a BigInteger from a Scanner: 
  - Scanner scanner = new Scanner (...); 
  - BigInteger n = scanner.nextBigInteger();

To compute the GCD of two BigIntegers: 
  - BigInteger n1 = ...; 
  - BigInteger n2 = ...; 
  - BigInteger p1 = n1.gcd (n2);

To divide two BigIntegers: 
  - BigInteger n = ...; 
  - BigInteger p = ...; 
  - BigInteger q = n.divide (p);

To test if two BigIntegers are equal: 
  - BigInteger a = ...; 
  - BigInteger b = ...; 
  - if (a.equals (b)) ...

To print a BigInteger: 
  - BigInteger n = ...; 
  - System.out.print (n); // or 
  - System.out.printf ("%s", n);
  
The RSA cracking program's input is stored in a plain text file. Each line of the file contains one RSA modulus. These RSA moduli might or might not have common factors.

The program reads the input file and creates multiple threads, one thread for each different pair of RSA moduli from the file. Each thread attempts to find the factors of its two RSA moduli, using the technique described above.

The program prints its results on the console. For each RSA modulus in the input file for which the program was able to find the factors, the program prints one line of output. The line consists of the modulus, a space character, one factor of the modulus, a space character, and the other factor of the modulus. The two factors may appear in either order on the line. The lines must appear in ascending order of the modulus.
