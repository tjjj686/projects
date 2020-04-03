package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode curr = list.front;
        ListNode pre = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        list.front = pre;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode f = list.front;
            ListNode next = list.front.next;
            f.next = null;
            ListNode tem = next;
            while (tem.next != null) {
                tem = tem.next;
            }
            tem.next = f;
            list.front = next;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    @SuppressWarnings("checkstyle:EmptyBlock")
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        LinkedIntList end = new LinkedIntList();
        if ((a.front == null)) {
            return b;
        } else {
            ListNode re = new ListNode(a.front.data);
            ListNode t1 = a.front;
            ListNode t2 = b.front;
            ListNode curr = re;
            while (t1.next != null) {
                curr.next = new ListNode(t1.next.data);
                curr = curr.next;
                t1 = t1.next;
            }
            while (t2 != null) {
                curr.next = new ListNode(t2.data);
                curr = curr.next;
                t2 = t2.next;
            }
            end.front = re;
            return end;
        }
    }
}
