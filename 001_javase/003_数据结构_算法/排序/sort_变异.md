# 在N个乱序的数组中找第K大的数 #
1. 快排 , 执行一次快速排序之后，每次只选择一部分继续执行快速排序，直到找到第K大个元素为止，这个元素在数组位置后面的元素即为所求。
2. 时间复杂度：O（n）
3. 利用快排的思想，从数组arr中随机找出一个元素X，把数组分成两部分arr_a和arr_b。


```
public class Quick_find {

	//  降序 的快排
	public static int partition(int[] arr,int low,int high){
		int temp=arr[low];
		while(low<high){
			// 降序
			while(arr[high]<=temp&&high>low)
				--high;
			arr[low]=arr[high];
			while(arr[low]>=temp&&low<high)
				++low;
			arr[high]=arr[low];
		}
		arr[high]=temp;
		return high;		
	}
	public static void find_k(int k,int[] arr,int low,int high){
		int temp=partition(arr,low,high);
		if(temp==k-1){
			System.out.print("第"+k+"大的数是："+arr[temp]);
		}else if(temp>k-1){
			find_k(k,arr,low,temp-1);			
		}else{
			// 后面~  k-temp
			find_k(k-temp,arr,temp+1,high);
		}
	}
	
 
	public static void main(String[] args) {
		int[] arr={3,1,2,5,4,7,6};
		find_k(2,arr,0,arr.length-1);
	}
 
}

```

# 找出数组中不重复的数-Java #
1. for 循环 异或
```
public static int NumberOf1(int[] arr) {
        int len = arr.length;
        int res = -1;
        if(len > 1) {
            res = arr[0];
            for (int i = 1; i < len; i++) {
                res = res ^ arr[i];
            }
        }
        return res;
}
```
