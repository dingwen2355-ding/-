//package com.grandlynn.util.impl.id;
//
//import com.google.common.base.Objects;
//import java.io.Serializable;
//import java.lang.management.ManagementFactory;
//import java.net.NetworkInterface;
//import java.nio.ByteBuffer;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class ObjectId implements Comparable<ObjectId>, Serializable {
//   private final int time = (int)(System.currentTimeMillis() / 1000L);
//   private final int machine;
//   private final int inc;
//   private boolean snew;
//   private static final int GENMACHINE;
//   private static AtomicInteger nextInc = new AtomicInteger((new Random()).nextInt());
//   private static final long serialVersionUID = -4415279469780082174L;
//   private static final Logger LOGGER = Logger.getLogger("org.bson.ObjectId");
//
//   public ObjectId() {
//      this.machine = GENMACHINE;
//      this.inc = nextInc.getAndIncrement();
//      this.snew = true;
//   }
//
//   public static ObjectId get() {
//      return new ObjectId();
//   }
//
//   public static boolean isValid(String s) {
//      if (s == null) {
//         return false;
//      } else {
//         int len = s.length();
//         int validlen = 24;
//         if (len != validlen) {
//            return false;
//         } else {
//            for(int i = 0; i < len; ++i) {
//               char c = s.charAt(i);
//               if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
//                  return false;
//               }
//            }
//
//            return true;
//         }
//      }
//   }
//
//   public String toHexString() {
//      StringBuilder buf = new StringBuilder(24);
//      byte[] var2 = this.toByteArray();
//      int var3 = var2.length;
//
//      for(int var4 = 0; var4 < var3; ++var4) {
//         byte b = var2[var4];
//         buf.append(String.format("%02x", b & 255));
//      }
//
//      return buf.toString();
//   }
//
//   public byte[] toByteArray() {
//      byte[] b = new byte[12];
//      ByteBuffer bb = ByteBuffer.wrap(b);
//      bb.putInt(this.time);
//      bb.putInt(this.machine);
//      bb.putInt(this.inc);
//      return b;
//   }
//
//   private int compareUnsigned(int i, int j) {
//      long li = 4294967295L;
//      li &= (long)i;
//      long lj = 4294967295L;
//      lj &= (long)j;
//      long diff = li - lj;
//      if (diff < -2147483648L) {
//         return Integer.MIN_VALUE;
//      } else {
//         return diff > 2147483647L ? Integer.MAX_VALUE : (int)diff;
//      }
//   }
//
//   public int compareTo(ObjectId id) {
//      if (id == null) {
//         return -1;
//      } else {
//         int x = this.compareUnsigned(this.time, id.time);
//         if (x != 0) {
//            return x;
//         } else {
//            x = this.compareUnsigned(this.machine, id.machine);
//            return x != 0 ? x : this.compareUnsigned(this.inc, id.inc);
//         }
//      }
//   }
//
//   public int getTimestamp() {
//      return this.time;
//   }
//
//   public Date getDate() {
//      return new Date((long)this.time * 1000L);
//   }
//
//   public static int getCurrentCounter() {
//      return nextInc.get();
//   }
//
//   public boolean equals(Object o) {
//      if (this == o) {
//         return true;
//      } else if (o != null && this.getClass() == o.getClass()) {
//         ObjectId that = (ObjectId)o;
//         long serialversionuid2 = -4415279469780082174L;
//         Logger logger2 = LOGGER;
//         AtomicInteger nextInc2 = nextInc;
//         int genmachine2 = GENMACHINE;
//         return Objects.equal(-4415279469780082174L, serialversionuid2) && Objects.equal(LOGGER, logger2) && Objects.equal(this.time, that.time) && Objects.equal(this.machine, that.machine) && Objects.equal(this.inc, that.inc) && Objects.equal(this.snew, that.snew) && Objects.equal(nextInc, nextInc2) && Objects.equal(GENMACHINE, genmachine2);
//      } else {
//         return false;
//      }
//   }
//
//   public int hashCode() {
//      return Objects.hashCode(new Object[]{-4415279469780082174L, LOGGER, this.time, this.machine, this.inc, this.snew, nextInc, GENMACHINE});
//   }
//
////   public static void main(String[] args) {
////      System.out.println((new ObjectId()).toHexString());
////      System.out.println((new ObjectId()).toHexString());
////      System.out.println((new ObjectId()).toHexString());
////   }
//
//   static {
//      try {
//         int machinePiece;
//         try {
//            StringBuilder sb = new StringBuilder();
//            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
//
//            while(e.hasMoreElements()) {
//               NetworkInterface ni = (NetworkInterface)e.nextElement();
//               sb.append(ni.toString());
//            }
//
//            machinePiece = sb.toString().hashCode() << 16;
//         } catch (Throwable var7) {
//            LOGGER.log(Level.WARNING, var7.getMessage(), var7);
//            machinePiece = (new Random()).nextInt() << 16;
//         }
//
//         LOGGER.fine("machine piece post: " + Integer.toHexString(machinePiece));
//         int processId = (new Random()).nextInt();
//
//         try {
//            processId = ManagementFactory.getRuntimeMXBean().getName().hashCode();
//         } catch (Throwable var6) {
//         }
//
//         ClassLoader loader = ObjectId.class.getClassLoader();
//         int loaderId = loader != null ? System.identityHashCode(loader) : 0;
//         StringBuilder sb = new StringBuilder();
//         sb.append(Integer.toHexString(processId));
//         sb.append(Integer.toHexString(loaderId));
//         int processPiece = sb.toString().hashCode() & '\uffff';
//         LOGGER.fine("process piece: " + Integer.toHexString(processPiece));
//         GENMACHINE = machinePiece | processPiece;
//         LOGGER.fine("machine : " + Integer.toHexString(GENMACHINE));
//      } catch (Exception var8) {
//         throw new RuntimeException(var8);
//      }
//   }
//}
