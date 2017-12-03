import java.io.IOException;

/*a*/
class CharArrayOps {
    
    CharArrayOps() {}
    
    static int[] readArray() {
        final int buffSize = 256;
        int[] buff = new int[buffSize];
        int c = 0, valid_chars = 0;
     
        while(valid_chars < buffSize) {
            try {
                c = System.in.read();
            } catch (IOException e) {
                c = 'X'; // like .. Xception ...
            }
            if (c != '\r' && c != '\n')  
                buff[valid_chars++] = c;
            else
                break;
        }
        
        //copy array 
        int[] str = new int[valid_chars];
        for(int j = 0; j < valid_chars; j++) {
            str[j] = buff[j];
        }   
         
        return str;
    }
    
    static void printArray(int[] array) {
        for(int j = 0; j < array.length; j++)
            System.out.print((char)array[j]);
        
        System.out.println();      
    }
    
    static int replaceChar(int[] array, int old_value, int new_value) {
        int changed = 0;
        for(int j = 0; j < array.length; j++) {
            if( array[j] == old_value ) {
                array[j] = new_value;
                changed++;
            } 
        }
        
        return changed;
        
    }
    
    static int findSubArray(int[] array, int[] array_to_find) {
        int match = 0, cnt2 = 0;
        int valid_chars2 = array_to_find.length;
        while(cnt2 < array.length) {
            if( array[cnt2++] == array_to_find[match] ) {
                if( match < valid_chars2 - 1)
                    match++;
                else
                    break;
            } else {
                match ^= match;
            }
        }
        
        return (cnt2 < array.length ? (cnt2 - valid_chars2 + 1) : -1);      
    }
    
    static int[] delChar(int[] array, int pos) {
        if(pos < array.length && pos >= 0) {
            
            int[] erased_string = new int[array.length - 1];
            int j = 0;
            
            for(int i = 0; i < array.length; i++) {
                if(i == pos) 
                    continue;
                erased_string[j++] = array[i];
            }
            
            return erased_string;
        
        } else {
            return array;
        }
    }
    
    static int[] delAllChars(int[] array, int char_to_del) {
        int arL = array.length, nmb_chars_to_del = 0;

        for(int i = 0; i < arL; i++) {
            if( array[i] == char_to_del)
                nmb_chars_to_del++;
        }
        
        if(nmb_chars_to_del > 0) {
            int er_length = arL - nmb_chars_to_del;
            int[] erased_string = new int[er_length];
        
            int k = 0;
            for(int i = 0; i < arL; i++) {
                if( array[i] == char_to_del) 
                    continue;
                erased_string[k++] = array[i];
            }
            
            return erased_string;
        } else {
            return array;
        } 
        
    }
    
    static int[] insertChar(int[] array, int toInsert, int pos) {
        if(pos < array.length && pos >= 0) {
            int nu_size = array.length + 1;
            int[] nu_string = new int[nu_size];
            int k = 0;
            for(int i = 0; i < array.length; i++) {
                if( i == pos) 
                    nu_string[k++] = toInsert; 
                
                nu_string[k++] = array[i];  
            }  
            
            return nu_string;
            
        } else {
            return array;
        }
        
    }
    
}
/*b*/
class HideInfo {
    
    HideInfo () {}
    
    static void substEIAU(int[] array) {
        CharArrayOps.replaceChar(array, (int)'e', (int)'i');
        CharArrayOps.replaceChar(array, (int)'a', (int)'u');
    }
    /*slow aF*/
    static int[] insertAtSecond(int[] array) {
        final int start = 1;
        int len = array.length;
        for(int i = start; i < len; i++)
            array = CharArrayOps.insertChar(array, (int)'e', 2*i - start);
        
        return array;
    }
    /*slow aF*/
    static int[] insertStrAtSecond(int[] array, int[] to_insert) {
        final int start = 1;
        int to_in_length = to_insert.length;
        int len = array.length;
        int j = 0;
        for(int i = start; i < len; i++) {
            array = CharArrayOps.insertChar(array, to_insert[j], 2*i - start);
            j = (j < to_in_length) ? j+1 : 0; // loops the to_insert string if its shorter than ... it should be..
            
        }
 
        return array;
    }
    
    static int[] clearVowels(int[] array) {
        int[] de_vowels = { 'a', 'e', 'i', 'o', 'u', 'y' };
        for( int i = 0; i < de_vowels.length; i++)
            array = CharArrayOps.delAllChars(array, de_vowels[i]);
        
        return array;
    }
}
/*c*/
class Caesar {
    
    static int encrypt(int[] array, int key) {
        int to_move = 0;
        if(key != 0) {
            key %= 26; // only to be able to have keys < -26 && > 26, not really necessary
            for(int i = 0; i < array.length; i++) 
                if( 'a' <= array[i] && array[i] <= 'z') { // don't 'encrypt' invalid chars, just ignore
                    to_move = (array[i] - 'a' + key) % ( 'z' - 'a' ); // 26
                    // convert to 'mathematical' modulo, in Java there is only symmetrical modulo/remainder available,
                    // <=> a mod m <=> ((a % m) + m) % m
                    if( to_move < 0 )
                        to_move += ( 'z' - 'a' );
                    
                    array[i] = 'a' + to_move;
                }  
                    
            return 1;
        } else
            return -1;   // nothing was 'encrypted'
        
    }
    
    static int decrypt(int[] array, int key) {
        return encrypt(array, -key);
        // or with positive keys..
        //return encrypt(array, (((key % 26) + 26) % 26));
    }
    
}

class OtherMethod {

    OtherMethod() {}
    
    static int encrypt(int[] array, int[] key) {
        for(int i = 0; i < array.length; i++)
            if( 'a' <= array[i] && array[i] <= 'z') { // don't 'encrypt' invalid chars, just ignore
                array[i] = key[array[i] - 'a'];
            }
        return 1;    
    }
    
    // use selection sort to sort the list
    static int[][] sortKeyList(int[][] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i][0] > array[j][0]) {
                    int temp = array[i][0];
                    int temp2 = array[i][1];
                    array[i][0] = array[j][0];
                    array[i][1] = array[j][1];
                    array[j][0] = temp;
                    array[j][1] = temp2;
                }
            }
        }

        return array;
    }
    
    static int[] calc_Inv_key(int[] key) {
        int alpha_size = key.length;
        // crate 2d array to later sort by chars, and be able to keep the indices
        int[][] sArray = new int[alpha_size][2];
        
        for(int i = 0; i < alpha_size; i++) {
            sArray[i][0] = key[i];
            sArray[i][1] = i + 'a'; 
        }
        
        sArray = sortKeyList(sArray);

        int[] invKey = new int[alpha_size];
        for(int i = 0; i < alpha_size; i++)
            invKey[i] = sArray[i][1];
        
        return invKey;
    }
    
    static int decrypt(int[] array, int[] key) {
        return encrypt(array, calc_Inv_key(key));  
    }
}

class Crypto_Tests {
    
    Crypto_Tests() {}
    
    static int arrcmp(int[] array1, int[] array2) {
        int i = 0, flag = 0; 
        if( array1.length != array2.length ) {
            return -1;
        } else {
            while (flag == 0) {
                if (array1[i] > array2[i]) flag = 1;
                else if (array1[i] < array2[i]) flag = -1;
                if (i >= array1.length - 1) break;
                i++;
            }
            return flag;
        }   
    }
    
    static int[] arrcpy(int[] source) {
        int[] buff = new int[source.length];
        for(int i = 0; i < source.length; i++)
            buff[i] = source[i];
        return buff;
    }
    
    static long read_number() {
        final int digits_to_read  = 19; // long
        char stelle_char = 0;
        long stelle = 0, zahl = 0;
        boolean valid_in = true;
        for ( int i = 0; i < digits_to_read; i++ ) {
            
            try {
                stelle_char = (char)System.in.read();
            } catch (IOException e) {
            }
            do {
                
                    if( stelle_char >= '0' && stelle_char <= '9') 
                        stelle = stelle_char - '0';
                    else if( stelle_char == '\r' || stelle_char == '\n' ) {
                        return zahl;
                    } else {
                        valid_in = false; break;
                    } 
                
                
            } while(false);
                
            if( !valid_in ) {
                System.out.println("- Machen Sie eine g\u00FCltige Eingabe: ");
                return 0;
            }
            /* Horner */
            zahl = zahl * 10 + stelle;
        }
        
        return zahl;
    }
    
    static int testCrypto(int method) throws IOException { // method == 0 -> Caesar; method == 1 -> OtherMethod; method == 2 -> XOR_Crypt
        int key = 0;
        long key2 = 0;
        int[] initTable = { 'm','h','i','o','y','x','r','a','n','c','q','d','w','j','g',
                            't','u','v','b','p','e','z','f','s','l','k' };
        System.out.println("Geben Sie eine Zeichenfolge ein und bestaetigen Sie ihre Eingabe mit Enter:");
        int[] str = CharArrayOps.readArray(); 
        int[] str_copy = arrcpy(str);
        
        System.out.println("Eingabe:");  
        CharArrayOps.printArray(str);
        
        if(method == 0) {
            System.out.println("Geben Sie eine Zahl im int-Bereich ein, die als Schluessel fuer die Caesar-Verschluesselung dienen soll, ein:");
            System.in.read(); //nl..
            System.in.read(); 
            key = System.in.read();
            key -= '0';
            Caesar.encrypt(str, key);
        } else if(method == 1) {
            OtherMethod.encrypt(str, initTable);
        } else if(method == 2) {
            System.out.println("Geben Sie eine Zahl im long-Bereich größer Null ein, die als Schluessel fuer die XOR-Verschluesselung dienen soll, ein:");
            System.in.read(); //nl..
            System.in.read();
            key2 = read_number();
            XOR_Crypt.encrypt(str, key2);
        }

        System.out.println("Verschluesselter String:");  
        CharArrayOps.printArray(str);
        System.out.println();
        
        if(method == 0) {
            Caesar.decrypt(str, key);
        } else if(method == 1) {
            OtherMethod.decrypt(str, initTable);
        } else if(method == 2) {
           XOR_Crypt.decrypt(str, key2); 
        }
        
        System.out.println("Entschluesselter String:");   
        CharArrayOps.printArray(str);
        System.out.println();
        
        System.out.println("Strings stimmen" + (arrcmp(str_copy, str) == 0 ? ' ' : " nicht") + "ueberein.");
        System.in.read();
        System.in.read();
        
        return 1;
 
    }
    
  
}
/*d*/
class Eavesdropper {
    
    Eavesdropper() {}
    
    static int[] brute_Caesar(int[] cipher, int[] known_plain) {
        
        if( cipher.length > 1 ) {
            int match = 0, cnt2 = 0;
            int valid_chars2 = known_plain.length;
            while(cnt2 < cipher.length) {
                if( (cipher[cnt2] - cipher[cnt2 + 1]) == (known_plain[match] - known_plain[match + 1]) ) {
                if( match < valid_chars2 - 1)
                    match++;
                else
                    break;
            } else {
                match ^= match;
            }
            cnt2++;
            }
            
            if( cnt2 >= cipher.length)
                return cipher;/*known_plain not in cipher*/
            
            int key = cipher[cnt2 - valid_chars2] - known_plain[0];
            
            Caesar.decrypt(cipher, key);
             
            
        } else if ( cipher.length == 1){
            cipher[0] = known_plain[0]; // assume plain is correct and first char of known_plain is first in cipher, can't do more
            
        } else {
             // nothing to brute
        }
        
        return cipher; 
    }
    
    static int[] brute_OtherMethod(int[] cipher, int[] known_plain) {
        return cipher;
    }
    
}
/*e*/
class XOR_Crypt {
    
    XOR_Crypt() {}
    
    /*q: how many possible values exist for "key" ? a: many.(2e64)*/
    static void encrypt(int[] str, long key) {
        int bt_cnt = 0;
        for(int i = 0; i < str.length; i++) {
           str[i] ^=  (key << 32*bt_cnt) & 0xff;
           bt_cnt = (bt_cnt < 1) ? bt_cnt+1 : 0; 
            
        }
        
    }
    
    static void decrypt(int[] array, long key) {
       encrypt(array, key);   
    }
    
}

public class CharArrayManipulation_Func  {

    public static void main(String[] args) throws Exception {
        boolean TEST_ARRAYOPS = false;
        if( TEST_ARRAYOPS ) {
        System.out.println("Geben Sie eine Zeichenfolge ein und bestaetigen Sie ihre Eingabe mit Enter:");
   
        int[] str = CharArrayOps.readArray(); 
        
        System.out.println("Eingabe:");
        
        CharArrayOps.printArray(str);
        
        System.out.println();
        
        System.out.println("Geben Sie nun einen Buchstaben ein, der in dem String ersetzt werden soll nach der Form a>b");
        
        System.in.read(); 
        int toChange = System.in.read();
        System.in.read();
        int changed = System.in.read();
        
        CharArrayOps.replaceChar(str, toChange, changed);
        
        
        System.out.println();
        
        System.out.println("geaenderte Eingabe:");
        
        CharArrayOps.printArray(str);
        
        System.out.println();
 
        
        System.out.println("Geben Sie nun eine Position an der ein Zeichen geloescht werden soll, 0 ist das erste Zeichen");
        System.in.read(); 
        System.in.read(); 
        int pos = System.in.read();
        pos -= '0';
        
        str = CharArrayOps.delChar(str, pos);

        System.out.println();
        
        System.out.println("geaenderte Eingabe:");
        
        CharArrayOps.printArray(str);

        System.out.println();
      
        System.out.println("Geben Sie nun ein Zeichen ein, dessen jedes Vorkommen geloescht werden soll");
        System.in.read();
        System.in.read(); 
        int char_to_del = System.in.read();
        
        str = CharArrayOps.delAllChars(str, char_to_del);
        
        System.out.println();
        System.out.println("geaenderte Eingabe:");
        
        CharArrayOps.printArray(str);

        System.out.println();
        System.out.println("Geben Sie nun eine Position an der ein Zeichen eingefuegt werden soll, 0 ist das erste Zeichen");
        System.in.read(); 
        System.in.read(); 
        pos = System.in.read();
        pos -= '0';
        
        str = CharArrayOps.insertChar(str, '-', pos);
        
        System.out.println();
        System.out.println("geaenderte Eingabe:");
        
        CharArrayOps.printArray(str);
        
        System.out.println();
        System.out.println("Geben Sie nun eine neue Zeichenkette ein, die in der vorhandenen gesucht werden soll");
        System.in.read(); 
        System.in.read(); 

        int[] substr = CharArrayOps.readArray();
        int found_at = CharArrayOps.findSubArray(str, substr);
        
        if ( found_at != -1)
            System.out.println("Zeichenkette gefunden an Position " + found_at);
        
        
        System.in.read(); 
        System.in.read(); 
        
        }
        
        boolean TEST_HIDEINFO = false;
        
        if( TEST_HIDEINFO ) {
            System.out.println("Geben Sie nun eine Zeichenkette ein, in der 'e's eingefuegt werden sollen");
            int[] str2 = CharArrayOps.readArray(); 
            str2 = HideInfo.insertAtSecond(str2);
            CharArrayOps.printArray(str2);
            System.out.println("Geben Sie nun nacheinander zwei neue Zeichenketten ein, die ineinander vermischt werden sollen");
            System.in.read(); 
            System.in.read();
            int[] str3 = CharArrayOps.readArray();           
            int[] str4 = CharArrayOps.readArray(); 

            str2 = HideInfo.insertStrAtSecond(str3, str4);
            CharArrayOps.printArray(str2);            
            
        }
        
        boolean TEST_CRYPTO = true;
        
        if( TEST_CRYPTO) {
        System.out.println();
        System.out.println("Crypto Tests:");
        System.out.println("Caesar:");
        Crypto_Tests.testCrypto(0);
        System.out.println();
        System.out.println("OtherCrypto:");
        Crypto_Tests.testCrypto(1);
        System.out.println();
        System.out.println("XOR:");
        Crypto_Tests.testCrypto(2);
        System.out.println();
        }
        
        System.in.read(); 
        System.in.read(); 
  
    }
}
