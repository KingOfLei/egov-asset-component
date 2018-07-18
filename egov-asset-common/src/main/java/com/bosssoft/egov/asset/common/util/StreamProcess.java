/*     */ package com.bosssoft.egov.asset.common.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class StreamProcess
/*     */ {
/*     */   public static int byteToInt(byte[] b)
/*     */   {
/*  23 */     return b[0] & 0xFF | (b[1] & 0xFF) << 8 | (b[2] & 0xFF) << 16 | (b[3] & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */   public static byte[] intToByte(int n)
/*     */   {
/*  35 */     byte[] b = new byte[4];
/*  36 */     b[3] = (byte)(n >> 24 & 0xFF);
/*  37 */     b[2] = (byte)(n >> 16 & 0xFF);
/*  38 */     b[1] = (byte)(n >> 8 & 0xFF);
/*  39 */     b[0] = (byte)(n & 0xFF);
/*  40 */     return b;
/*     */   }
/*     */ 
/*     */   public static void writeInteger(OutputStream stream, int value)
/*     */     throws IOException
/*     */   {
/*  52 */     byte[] buf = intToByte(value);
/*  53 */     stream.write(buf);
/*     */   }
/*     */ 
/*     */   public static void writeString(OutputStream stream, String value)
/*     */     throws IOException
/*     */   {
/*  65 */     if (value == null)
/*  66 */       value = "";
/*  67 */     byte[] stringBytes = value.getBytes();
/*  68 */     int len = stringBytes.length;
/*  69 */     writeInteger(stream, len);
/*  70 */     stream.write(stringBytes);
/*     */   }
/*     */ 
/*     */   public static void writeStream(OutputStream mainStream, ByteArrayOutputStream subStream)
/*     */     throws IOException
/*     */   {
/*  86 */     byte[] buf = subStream.toByteArray();
/*  87 */     writeInteger(mainStream, buf.length);
/*  88 */     mainStream.write(buf);
/*     */   }
/*     */ 
/*     */   public static void writeStream(OutputStream mainStream, InputStream subStream)
/*     */     throws IOException
/*     */   {
/* 103 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 104 */     byte[] b = new byte[512];
/* 105 */     int len = 0;
/* 106 */     while ((len = subStream.read(b)) > 0) {
/* 107 */       stream.write(b, 0, len);
/*     */     }
/*     */ 
/* 110 */     byte[] buf = stream.toByteArray();
/* 111 */     writeInteger(mainStream, buf.length);
/* 112 */     mainStream.write(buf);
/* 113 */     stream.flush();
/* 114 */     stream.close();
/* 115 */     stream = null;
/*     */   }
/*     */ 
/*     */   public static int readInteger(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 127 */     byte[] buf = new byte[4];
/* 128 */     stream.read(buf);
/* 129 */     return byteToInt(buf);
/*     */   }
/*     */ 
/*     */   public static String readLenString(InputStream stream, int len)
/*     */     throws IOException
/*     */   {
/* 143 */     byte[] buf = new byte[len];
/* 144 */     stream.read(buf);
/* 145 */     return new String(buf);
/*     */   }
/*     */ 
/*     */   public static String readString(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 157 */     int len = readInteger(stream);
/* 158 */     return readLenString(stream, len);
/*     */   }
/*     */ 
/*     */   public static byte[] readBuffer(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 170 */     int len = readInteger(stream);
/* 171 */     byte[] buf = new byte[len];
/* 172 */     stream.read(buf);
/* 173 */     return buf;
/*     */   }
/*     */ 
/*     */   public static String getClassName(String value)
/*     */   {
/* 184 */     String[] array = value.replace('.', ';').split(";");
/* 185 */     String className = array[(array.length - 1)];
/* 186 */     return className;
/*     */   }
/*     */ 
/*     */   public static String toHexString2(byte[] b)
/*     */   {
/* 197 */     StringBuffer buffer = new StringBuffer();
/* 198 */     for (int i = 0; i < b.length; i++)
/*     */     {
/* 200 */       buffer.append(toHexString2(b[i]));
/*     */     }
/* 202 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String toHexString2(byte b)
/*     */   {
/* 213 */     char[] buffer = new char[2];
/* 214 */     buffer[0] = Character.forDigit(b >>> 4 & 0xF, 16);
/* 215 */     buffer[1] = Character.forDigit(b & 0xF, 16);
/* 216 */     return new String(buffer);
/*     */   }
/*     */ 
/*     */   public static String toHexString1(byte b)
/*     */   {
/* 227 */     String str = Integer.toHexString(b & 0xFF);
/* 228 */     if (str.length() == 1) {
/* 229 */       return "0" + str;
/*     */     }
/* 231 */     return str;
/*     */   }
/*     */ 
/*     */   public static String toHexString1(byte[] b)
/*     */   {
/* 243 */     StringBuffer buffer = new StringBuffer();
/* 244 */     for (int i = 0; i < b.length; i++)
/*     */     {
/* 246 */       buffer.append(toHexString1(b[i]));
/*     */     }
/* 248 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static byte[] stringToByte(String value)
/*     */   {
/* 259 */     byte[] buf = new byte[value.length() / 2];
/* 260 */     String str = "";
/* 261 */     for (int i = 0; i < value.length() / 2; i++)
/*     */     {
/* 263 */       str = value.substring(i * 2, i * 2 + 2);
/* 264 */       buf[i] = Integer.decode("0x" + str).byteValue();
/*     */     }
/* 266 */     return buf;
/*     */   }
/*     */ 
/*     */   public static String byteToString(byte[] b)
/*     */     throws IOException
/*     */   {
/* 279 */     return new String(b, 0, b.length, "GBK");
/*     */   }
/*     */ 
/*     */   public static String streamToString(InputStream stream) throws IOException {
/* 293 */     ByteArrayOutputStream tmpStream = new ByteArrayOutputStream();
/* 294 */     byte[] b = new byte[512];
/*     */     int size;
/*     */     do {
/* 298 */       size = stream.read(b);
/* 299 */       tmpStream.write(b);
/* 300 */     }while (size != -1);
/* 301 */     byte[] buf = tmpStream.toByteArray();
/* 302 */     tmpStream.flush();
/* 303 */     tmpStream.close();
/* 304 */     tmpStream = null;
/* 305 */     return byteToString(buf);
/*     */   }
/*     */ 
/*     */   public static String streamToString(ByteArrayOutputStream stream)
/*     */     throws Exception
/*     */   {
/* 318 */     byte[] buf = stream.toByteArray();
/* 319 */     return byteToString(buf);
/*     */   }
/*     */ }