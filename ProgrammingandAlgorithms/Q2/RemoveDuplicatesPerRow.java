/*
Explanation of the Approach

The idea is simple — go through each row in the 2D array and remove any duplicate numbers 
by replacing them with 0. Each row is handled separately, so duplicates are only checked 
within the same row.

Since built-in helpers like contains() or HashSet aren’t allowed, the code builds its own 
basic lookup system — a kind of mini hash table. Two arrays are used for that:
- one (hashTable) to store numbers that have already appeared,
- another (occupied) to mark which positions in that table are taken.

For each number in the row:
1. A hash index is calculated using Math.abs(val) % hashSize.
2. If that spot in the table is empty, the number is recorded as new.
3. If it’s occupied, we check if the same number is already there:
   - If yes, it’s a duplicate, so the number in the row is changed to 0.
   - If not, the code moves ahead (linear probing) until it finds an open slot.

By the end, only the first occurrence of each number remains; all later repeats 
have been turned to zeros.

Time Complexity

Let:
- n be the number of rows,
- m be the number of elements in a row (which can differ for each row).

Each element is processed once, and hash lookups take roughly constant time, 
so the work per row is about O(m). Across all rows, the total time is 
O(total number of elements).

In the rare case that many numbers collide in the hash table, probing might make it 
slower — up to O(m²) — but in practice, with a table twice the row’s length, 
it stays close to linear.
*/



package Q2;
public class RemoveDuplicatesPerRow {

    public static void main(String[] args) {
        int[][] input = {
            {1, 3, 1, 2, 3, 4, 4, 3, 5},
            {1, 1, 1, 1, 1, 1, 1}
        };

        int[][] output = removeDuplicates(input);

        // Print result
        for (int[] row : output) {
            System.out.print("{ ");
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println("}");
        }
    }

    public static int[][] removeDuplicates(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            int hashSize = row.length * 2; //  reduce collisions
            int[] hashTable = new int[hashSize];
            boolean[] occupied = new boolean[hashSize];

            for (int j = 0; j < row.length; j++) {
                int val = row[j];
                if (val == 0) continue; // skip zeros

                int index = Math.abs(val) % hashSize;
                boolean isDuplicate = false;

                // Linear probing
                while (occupied[index]) {
                    if (hashTable[index] == val) {
                        isDuplicate = true;
                        break;
                    }
                    index = (index + 1) % hashSize;
                }

                if (isDuplicate) {
                    row[j] = 0; // mark duplicate
                } else {
                    hashTable[index] = val;
                    occupied[index] = true;
                }
            }
        }
        return arr;
    }
}

