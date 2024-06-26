import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

class Solutions {
    public static void main(String args[]) {
        Solutions sol = new Solutions();
        int numSub = sol.countCompleteSubarrays(new int[]{1,3,1,2,2});
        int maxlen = sol.maxSubarrayLength(new int[]{1,2,3,1,2,3,1,2}, 2);
        int maxLenTwo = sol.maximumLengthSubstringTwo("bcbbbcba");
        int maxErase = sol.maxErasureValue(new int[]{4,2,4,5,6});

        List<Integer> list = new ArrayList<>(Arrays.asList(8,3,9,9,9,9,6,2,6,6,9,1,9));
        int maxSubLen = sol.longestEqualSubarray(list, 0);
        int minSizeArray = sol.minSubArrayLen(11, new int[] {1,2,3,4,5});
        int middleIndex = sol.findMiddleIndex(new int[]{2,3,-1,8,4});
        System.out.println(middleIndex);
    }

    // removing trailing zeroes
    public String removeTrailingZeros(String num) {
        char[] charArray = num.toCharArray();
        Stack<Character> stack = new Stack<>();
        boolean check = false;
        for (int i = charArray.length - 1; i >= 0; i--) {
            char currentLastChar = charArray[i];
            if (currentLastChar == '0' && !check) {
                continue;
            }
            stack.push(currentLastChar);
            check = true;
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }
        return sb.toString();
    }


    public String pushDominoes(String dominoes) {
        char[] array = dominoes.toCharArray();
        class Helper{
            int[] populate(int arrayLength){
                int [] array = new int[arrayLength];
                for (int i = 0; i < arrayLength; i++){
                    array[i] = 0;
                }
                return array;
            }
        }
        Helper help = new Helper();
        int [] leftForce = help.populate(array.length);
        int [] rightForce = help.populate(array.length);
        StringBuilder sb = new StringBuilder();
        int charForce = 0;
        for (int i = 0; i < array.length; i++){
            int localChar = array[i];
            if (localChar == 'R'){
                charForce = array.length;
            }
            if (localChar == '.'){
                charForce -= 1;
            }
            if (localChar == 'L'){
                charForce = 0;
            }
            rightForce[i] = Math.max(0, charForce);
        }
        charForce = 0;
        for (int j = array.length - 1; j >= 0; j--){
            int localChar = array[j];
            if (localChar == 'R'){
                charForce = 0;
            }
            if (localChar == '.'){
                charForce -= 1;
            }
            if (localChar == 'L'){
                charForce = array.length;
            }
            leftForce[j] = Math.max(0, charForce);
        }
        for (int i = 0; i < leftForce.length; i++){
            int left = leftForce[i];
            int right = rightForce[i];
            if (left == right){
                sb.append('.');
            }
            if (left > right){
                sb.append('L');
            }
            if (right > left){
                sb.append('R');
            }
        }
        return sb.toString();
    }

    public int longestMountain(int[] arr){
        int maxLen = 0;
        int end = 0;
        int localCounter = 0;
        while (end < arr.length){
            if (end > 0 && arr[end] > arr[end - 1]){
                    localCounter = 1;
                    boolean checkUp = false;
                    boolean checkDown = false;
                    while (end < arr.length && arr[end] > arr[end - 1]){
                        checkUp = true;
                        localCounter++;
                        end++;
                    }
                    // check down for slope
                    while(end < arr.length && arr[end] < arr[end - 1]){
                        checkDown = true;
                        localCounter++;
                        end++;
                    }
                    // update the new maxLen
                    if(checkDown && checkUp){
                        maxLen = Math.max(localCounter, maxLen);
                    }
            }else{
                end++;
            }
        }
        return maxLen;
    }

    // creating parition labels by creating intervals
    public List<Integer> partitionLabels (String s){
        // variables
        List<Integer> labelList = new ArrayList<>();
        char[] array = s.toCharArray();
        HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<ArrayList<Integer>> intervalContainer = new ArrayList<>();
        // get the intervals
        for (int i = 0; i < array.length; i++){
            char currLetter = array[i];
            if (!map.containsKey(currLetter)){
                map.put(currLetter, new ArrayList<>());
                map.get(currLetter).add(i);
                map.get(currLetter).add(0);
            }
            if (map.containsKey(currLetter)){
                map.get(currLetter).set(1, i);
            }
        }
        for (ArrayList<Integer> valueSet: map.values()){
            intervalContainer.add(valueSet); // addAll allows to add entire entire array or collection
        }
        // sorting intervals in order to get indices
        intervalContainer.sort((a, b)-> Integer.compare(a.get(0), b.get(0)));

        // looping and creating comparison
        int start = intervalContainer.get(0).get(0);
        int end = intervalContainer.get(0).get(1);

        for (int i = 1; i < intervalContainer.size(); i++){
            int currStart = intervalContainer.get(i).get(0);
            int currEnd = intervalContainer.get(i).get(1);

            if (currStart >= end){
                labelList.add(end - start + 1);
                end = currEnd;
                start = currStart;
                if (i == intervalContainer.size() - 1){
                    labelList.add(end - start + 1);
                }
                continue;
            }
            if (currStart <= start){
                start = currStart;
            }
            if (currEnd >= end ){
                end = currEnd;
            }
            if (i == intervalContainer.size() - 1){
                labelList.add(end - start + 1);
            }
        }
        return labelList;
    }


    // removing all anagrams
    public List<Integer> findAnagrams(String s, String p) {
        // gen variables // length() is for strings and length is for arrays
        if (p.length() > s.length()) {
            return new ArrayList<>();
        }
        List<Integer> listIndices = new ArrayList<>();
        char[] sArray = s.toCharArray();
        char[] pArray = p.toCharArray();
        class Occurence {
            HashMap<Character, Integer> getOccurence(char[] array, int len){
                HashMap<Character, Integer> map = new HashMap<>();
                for (int i = 0; i < len; i++){
                    char key = array[i];
                    if (map.containsKey(key)){
                        map.put(key, map.get(key) + 1);
                    }else{
                        map.put(key, 1);
                    }
                }
                return map;
            }
        }
        Occurence oc = new Occurence();
        HashMap<Character, Integer> sMap = oc.getOccurence(sArray, pArray.length);
        HashMap<Character, Integer> pMap = oc.getOccurence(pArray, pArray.length);
        if (sMap.equals(pMap)){
            listIndices.add(0);
        }
        // main sliding window check
        int start = 0;
        for (int i = pArray.length; i < sArray.length; i++){
            char currLetter = sArray[i];
            char firstLetter = sArray[start];
            if (sMap.containsKey(firstLetter)){
                sMap.put(firstLetter, sMap.get(firstLetter) - 1);
                if(sMap.get(firstLetter) == 0){
                    sMap.remove(firstLetter);
                }
            }
            if(sMap.containsKey(currLetter)){
                sMap.put(currLetter, sMap.get(currLetter) + 1);
            }else{
                sMap.put(currLetter, 1);
            }
            start++;
            if(sMap.equals(pMap)){
                listIndices.add(start);
            }
        }

        return listIndices;
    }

    // intersection of arrays finding elements present in each of them... note they are distinct
    public List<Integer> intersection(int[][] nums) {
        List<Integer> finalList = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++){
            int [] row = nums[i];
            for (int j = 0; j < row.length; j++){
                int element = nums[i][j];
                if (map.containsKey(element)){
                    map.put(element, map.get(element) + 1);
                }else{
                    map.put(element, 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry: map.entrySet()){
            int key = entry.getKey();
            int value = entry.getValue();
            if (value == nums.length){
                finalList.add(key);
            }
        }
        // sorting 5,4,3,2-> 4 5 3 2 

        for (int i = 0; i < finalList.size(); i++){
            for (int j = i + 1; j < finalList.size(); j++ ){
                if (finalList.get(i) > finalList.get(j)){
                    int temp = finalList.get(i);
                    finalList.set(i, finalList.get(j));
                    finalList.set(j, temp);
                }
            }
        }
        return finalList;
    }

    // removing adjacent chars
    public String makeGood(String s) {
        char[] sArray = s.toCharArray();
        ArrayList<Character> stack = new ArrayList<>(); // use stack instead
        stack.add(sArray[0]);

        for (int i = 1; i < sArray.length; i++){
            char currLetter = sArray[i];
            if (!stack.isEmpty() && Character.toLowerCase(currLetter) == Character.toLowerCase(stack.get(stack.size() - 1))){
                char stackLast = stack.get(stack.size() - 1);
                if (stackLast != currLetter){
                    stack.remove(stack.size() - 1);
                }else{
                    stack.add(currLetter);
                }
            }else{
                stack.add(currLetter);
            }
        }

        if (stack.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char letter: stack){
            sb.append(letter);
        }
        return sb.toString();
    }

    // find three consequtive number equal to a number
    public long[] sumOfThree(long num) {
        long[] result = new long[]{0, 0, 0};
        long limit =  num / 3;
        float limitCheck = (float) num / 3;
        if (limit == limitCheck){
            long newLimit = num / 3;
            result[0] = newLimit - 1;
            result[1] = newLimit;
            result[2] = newLimit + 1;
            return result;
        }
        return new long[]{};
    }


    // finding the max frequency after K increments;
    public int maxFrequency(int[] nums, int k) {
        long maxLen = 0;
        int start = 0;
        int end = 0;
        int total = 0;
        Arrays.sort(nums);
        
        while (end < nums.length) {
            total += nums[end];
            while (start <= end && nums[end] * (end - start + 1L) > total + k) {
                total -= nums[start];
                start++;
            }
            maxLen = Math.max(end - start + 1L, maxLen);
            if (end == nums.length - 1){
                break;
            }
            end++;
        }   
        return (int) maxLen;
    }


    // keyboard row
    public String[] findWords(String[] words) {
        ArrayList<String> result = new ArrayList<>();

        // function to get ooccurence
        Function<char[], HashMap<Character, Integer>> getOccurence = chars ->{
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < chars.length; i++){
                map.put(chars[i], 1);
            }
            return map;
        };

        HashMap<Character, Integer> rowOne = getOccurence.apply("qwertyuiopQWERTYUIOP".toCharArray());
        HashMap<Character, Integer> rowTwo = getOccurence.apply("asdfghjklASDFGHJKL".toCharArray());
        HashMap<Character, Integer> rowThree = getOccurence.apply("zxcvbnmZXCVBNM".toCharArray());

        for (String word: words){
            boolean checkOne = false;
            boolean checkTwo = false;
            boolean checkThree = false;
            for (int i = 0; i < word.length(); i++){
                char currLetter = word.charAt(i);
                if (rowOne.containsKey(currLetter)){
                    checkOne = true;
                }
                if (rowTwo.containsKey(currLetter)){
                    checkTwo = true;
                }
                if (rowThree.containsKey(currLetter)){
                    checkThree = true;
                }
        
            }
            if (checkOne && !checkTwo && !checkThree){
                result.add(word);
            }
            if (!checkOne && !checkTwo && checkThree){
                result.add(word);
            }
            if (!checkOne && checkTwo && !checkThree){
                result.add(word);
            }
        }

        return result.toArray(new String[0]);
    }

    // cyclic sorting question
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        List<Integer> result = new ArrayList<>();
        int capacity = grid.length * grid.length;
        List<Integer> range = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= capacity; i++){
            range.add(i);
        }
        // main traversal
        for (int i = 0; i < grid.length; i++){
            int [] row = grid[i];
            for(int j = 0; j < row.length; j++){
                int el = row[j];
                if (map.containsKey(el)){
                    map.put(el, map.get(el) + 1);
                    result.add(el);
                }else{
                    map.put(el, 1);
                }
            }
        }
        for (int i = 0; i < range.size(); i++){
            int el = range.get(i);
            if (!map.containsKey(el)){
                result.add(el);
                break;
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }


    // maximum road connected network making a map and graphinc routes
    // public int maximalNetworkRank(int n, int[][] roads) {
    //     int max = 0;
    //     HashMap<Integer, Integer> map = new HashMap<>();
    //     for (int [] edge: roads){
    //         map.put(edge[0], edge[1]);
    //         map.put(edge[1], edge[0]);
    //     }
    //     System.out.println(map);
    //     return max;
    // }

    // detecting the max sub between two chars
    public int maxLengthBetweenEqualCharacters(String s) {
        char[] array = s.toCharArray();
        int maxDistance = -1;
        HashMap<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < array.length; i++){
            char localChar = array[i];
            if (map.containsKey(localChar)){
                map.get(localChar)[1] = i;
            }else{
                map.put(localChar, new int[]{i, i});
            }
        }
        for (Map.Entry<Character, int[]>entry : map.entrySet()){
            int [] range = entry.getValue();
            if (range[0] != range[1]){
                maxDistance = Math.max(range[0] == range[1] ? 0 :range[1] - range[0] - 1, maxDistance);
            }
        }
        return maxDistance;
    }

    // sum of hour glass
    public int maxSumHourglass(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length - 2; i++){
            int [] row = grid[i];
            int localSum = 0;
            for (int j = 0; j < row.length - 2; j++){
                int currEl = grid[i][j];
                int currRight = grid[i][j + 1];
                int currFarRight = grid[i][j + 2];
                int currCenter = grid[i + 1][j + 1];
                int currLeftBottom = grid[i + 2][j];
                int currBottomCenter = grid[i + 2][j + 1];
                int currBottomRight = grid[i + 2][j + 2];
                localSum = currEl + currRight + currFarRight + currCenter + currLeftBottom + currBottomRight + currBottomCenter;
                max = Math.max(max, localSum);
            }
        }
        return max;
    }

    // longest palindrome after concatenating two letters
    public int longestPalindrome(String[] words) {
        HashMap<String, Integer> mapNonPal = new HashMap<>();
        HashMap<String, Integer> mapDoubles = new HashMap<>();
        for(String word: words){
            if (word.charAt(0) != word.charAt(1)){
                mapNonPal.put(word, (mapNonPal.getOrDefault(word,0) + 1));
            }else{
                mapDoubles.put(word,(mapDoubles.getOrDefault(word, 0) + 1));
            }
        }
        //get the non values
        int nonPalLen = 0;
        int doubleLen = 0;
        for (Map.Entry<String, Integer> entry: mapNonPal.entrySet()){
            String currValue = entry.getKey();
            StringBuilder revKey = new StringBuilder();
            revKey.append(currValue.charAt(1));
            revKey.append(currValue.charAt(0));
            if (mapNonPal.containsKey(revKey.toString())){
                nonPalLen += 4 * Math.min(mapNonPal.get(revKey.toString()), mapNonPal.get(currValue));
                mapNonPal.put(revKey.toString(), 0);
            }
        }
        // for double palindrome
        Set<Map.Entry<String, Integer>> entrySetDouble = new HashSet<>(mapDoubles.entrySet());
        boolean leftOvers = false;
        for (Map.Entry<String, Integer> entry: entrySetDouble){
            int val = entry.getValue();
            if (val % 2 == 0){
                doubleLen += val * 2;
            }
            if (val % 2 == 1){
                doubleLen += val * 2 - 2;
                leftOvers = true;
            }  
        }
        return leftOvers ? doubleLen + nonPalLen + 2: doubleLen + nonPalLen;

    }

    // maximum length
    public int maximumLengthOccursThrice(String s) {
        char[] array = s.toCharArray();
        int max = -1;
        HashMap<String, Integer> map = new HashMap<>();

        for(int i = 0; i < array.length; i++){
            char checkChar = array[i];
            int localLen = 1; // initial length of one char
            StringBuilder sb = new StringBuilder();
            sb.append(checkChar);
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
            for (int j = i + 1; j < array.length; j++){
                if (array[j] == array[j - 1]){ // check for continuation
                    localLen++;
                    sb.append(array[j]);
                    if (map.containsKey(sb.toString())){
                        map.put(sb.toString(), map.get(sb.toString()) + 1);
                    }else{
                        map.put(sb.toString(), 1);
                    }
                }   else{// no continuation here
                    break;
                }
            }   
        }
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            if (entry.getValue() >= 3){
                max = Math.max(entry.getKey().length(), max);
            }
        }
        return max;
    }

    // with additional constraints
    public int maximumLengthII(String s) {
        int max = -1;
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++){
            char startChar = s.charAt(i);
            int len = 1;
            StringBuilder sb = new StringBuilder();
            sb.append(startChar);
            String sbString = sb.toString();
            map.put(sbString, map.getOrDefault(sbString, 0) + 1);
            for (int j = i + 1; j < s.length(); j++){  
                if (s.charAt(j) == s.charAt(j - 1)){
                    len++;
                    sb.append(s.charAt(j));
                    String sbPString = sb.toString();
                    map.put(sbPString, map.getOrDefault(sbPString, 0) + 1);
                }else{
                    break;
                }
            }
        }
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            if (entry.getValue() >= 3){
                max = Math.max(max, entry.getKey().length());
            }
        }
        return max;
    }

    // number of distinct subarrays is equal to the distinct el in the entire arrays
    public int countCompleteSubarrays(int[] nums) {
        int counter = 0;
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> subSet = new HashSet<>();
        for (int num: nums){
            set.add(num);
        }
        for (int i = 0; i < nums.length; i++){
            subSet.clear();
            for (int j = i; j < nums.length; j++){
                subSet.add(nums[j]);
                if (subSet.size() == set.size()){
                    counter++;
                }
            }

        }
        return counter;
    }

    // number of occurence should be less than or equal to k max .. shrink window when it exceeds k
    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int end = 0;
        int start = 0;
        int maxLen = 0;
        while(end < nums.length){
            int currEl = nums[end];
            map.put(currEl, map.getOrDefault(currEl, 0) + 1);
            while (map.get(nums[end]) > k){
                if (map.containsKey(nums[start])){
                    map.put(nums[start], map.get(nums[start]) - 1);
                    if (map.get(nums[start]) == 0){
                        map.remove(nums[start]);
                    }
                }
                start++;
            }
            maxLen = Math.max(end - start + 1, maxLen);
            end++;
        }
        return maxLen;
    }

    // max two occurence of a single char
    public int maximumLengthSubstringTwo(String s) {
        int max = 0;
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        for(int i = 0; i < s.length(); i++){
            char currChar = s.charAt(i);
            map.put(currChar, map.getOrDefault(currChar, 0) + 1);
            while(map.get(s.charAt(i)) > 2){
                if(map.containsKey(s.charAt(start))){
                    map.put(s.charAt(start), map.get(s.charAt(start)) - 1);
                    if(map.get(s.charAt(start)) == 0){
                        map.remove(s.charAt(start));
                    }
                }
                start++;
            }
            max = Math.max(i - start + 1, max);
        }
        return max;
    }


    // maximum erasure value
    public int maxErasureValue(int[] nums) {
        int max = 0;
        int total = 0;
        int start = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            int currNum = nums[i];
            total += currNum;
            map.put(currNum, map.getOrDefault(currNum, 0) + 1);
            while(map.get(currNum) > 1){
                total -= nums[start];
                if (map.containsKey(nums[start])){
                    map.put(nums[start], map.get(nums[start]) - 1);
                    if(map.get(nums[start]) == 0){
                        map.remove(nums[start]);
                    }
                }
                start++;
            }
            max = Math.max(max, total);
        }
        return max;
    }

    // longest subarray after deletion
    public int longestEqualSubarray(List<Integer> nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int end = 0;
        int start = 0;
        int maxFreq = 0;
        while(end < nums.size()){
            int currEl = nums.get(end);
            map.put(currEl, map.getOrDefault(currEl, 0) + 1);
            maxFreq = Math.max(maxFreq, map.get(currEl));
      
            if((end - start + 1 - maxFreq > k)){
                map.put(nums.get(start), map.get(nums.get(start)) - 1);
                if (map.get(nums.get(start)) == 0){
                    map.remove(nums.get(start));
                }

                start++;
            }
            end++;
        }
        return maxFreq;
    }


    // minimum size subarrays
    // simple sliding window approach
    public int minSubArrayLen(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;
        int total = 0;
        int start = 0;
        for (int end = 0; end < nums.length; end++){
            int currEl = nums[end];
            total += currEl;
            while(total >= target){
                minLength = Math.min(minLength, end - start + 1);
                total -= nums[start];
                start++;
            }
        }
        return minLength == 2147483647 ? 0 : minLength;
    }


    // checking middle index... same question as pivot index
    public int findMiddleIndex(int[] nums) {
        int left = 0;
        int prefixSum = 0;
        int totalSum = 0;
        for (int num:nums){
            totalSum += num;
        }
        while(left < nums.length){
            if (prefixSum == totalSum - prefixSum - nums[left]){ // current element need to minused
                return left ;
            }
            prefixSum += nums[left];
            left++;
        }
        return -1;
    }

}