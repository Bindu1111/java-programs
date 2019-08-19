import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.util.Random;

public class SortedArray {

   public static void main(String args[] ) throws Exception {  

         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

         String line = br.readLine();

         int N = Integer.parseInt(line);

         for (int i = 0; i < N; i++) {

          line=br.readLine();

          int k=Integer.parseInt(line);

          int arr[]=new int[k];

          line=br.readLine();

          String str[]=line.split(" ");

           for(int p=0;p<str.length;p++){

            arr[p]=Integer.parseInt(str[p]);

           }

          int[] ser=quickSort(arr, 0,arr.length-1);

          for(int d=0;d<ser.length;d++){

           System.out.print(ser[d]+" ");

          }    System.out.println();

          }

      }


  public  static int partition(int[] arr1,int start,int end) {

   int pivot = arr1[end];

   int partitionIndex=start;

   for(int i=start;i<=end;i++){

    if(arr1[i]>pivot){

     int tmp=arr1[i];

     arr1[i]=arr1[partitionIndex];

     arr1[partitionIndex]=tmp;

     partitionIndex++;

    }

   }

   int tmp=arr1[partitionIndex];

   arr1[partitionIndex]=arr1[end];

   arr1[end]=tmp;

   return partitionIndex;

  }

  public static int[] quickSort(int[] arr1,int start,int end){

   if(start<end){

    int partitionIndex=partition(arr1, start, end);

    quickSort(arr1, start, partitionIndex-1);

    quickSort(arr1, partitionIndex+1, end);

   }

   return arr1;

  }

}