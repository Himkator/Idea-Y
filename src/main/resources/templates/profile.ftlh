public static void printUniqueElements(int[] array){
//пишите код сюда
int len=array.length;
boolean b=true;
for(int i=0; i<len; i++){
for(int j=0; j<len; j++){
if(i==j) continue;
if(array[i]==array[j]){
b=false;
break;
}
else b=true;
}
if(b) System.out.print(array[i]+" ");
}
}