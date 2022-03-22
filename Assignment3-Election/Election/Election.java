package col106.assignment3.Election;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import col106.assignment3.BST.BST;

public class Election implements ElectionInterface {
	/*
	 * Do not touch the code inside the upcoming block If anything tempered your
	 * marks will be directly cut to zero
	 */
	public static void main() {
		ElectionDriverCode EDC = new ElectionDriverCode();
		System.setOut(EDC.fileout());
	}
	/*
	 * end code
	 */

	// write your code here

	private BST<String, Integer> idVote = new BST<>(); // <CandID, Votes>
	private HashMap<String, String[]> data = new HashMap<>(); // all Candidate data
	private HashMap<String, BST<String, Integer>> topk = new HashMap<>(); // <Constituency, BST<CandID, Votes>> for topk Constituency
	private HashMap<String, BST<String, Integer>> State = new HashMap<>(); // <State, BST<Party, Votes> for leading party
	private BST<String, Integer> overall = new BST<>(); // <Party, Votes> Overall leading Party
	private HashMap<String, Integer> statevote = new HashMap<>(); // <State, TotalVote> Statewise Total votes

	public void insert(String name, String candID, String state, String district, String constituency, String party,
			String votes) {
		int vote = Integer.parseInt(votes);
		idVote.insert(candID, vote);
		String[] data = new String[] { name, state, district, constituency, party, votes };
		this.data.put(candID, data);

		if (topk.containsKey(constituency)) {
			topk.get(constituency).insert(candID, vote);
		} else {
			topk.put(constituency, new BST<String, Integer>());
			topk.get(constituency).insert(candID, vote);
		}

		if (State.containsKey(state)) {
			if (State.get(state).hasKey(party)) {
				int newVote = vote + State.get(state).getValue(party);
				State.get(state).update(party, newVote);
			} else {
				State.get(state).insert(party, vote);
			}

		} else {
			State.put(state, new BST<String, Integer>());
			State.get(state).insert(party, vote);
		}

		// idVote, data, topk, State, overall, statevote

		if (overall.hasKey(party)) {
			overall.update(party, overall.getValue(party) + vote);
		} else {
			overall.update(party, vote);
		}

		if (statevote.containsKey(state)) {
			statevote.put(state, statevote.get(state) + vote);
		} else {
			statevote.put(state, vote);
		}

		// write your code here
	}

	public void updateVote(String name, String candID, String votes) {
		// write your code here
		// idVote, data, topk, State, overall, statevote
		int vote = Integer.parseInt(votes);
		idVote.update(candID, vote);
		String[] d = data.get(candID).clone(); // d: name, state, district, constituency, party, votes
		String[] newd = d.clone();

		newd[5] = votes;

		data.put(candID, newd);

		topk.get(d[3]).update(candID, vote);

		int change = vote - Integer.parseInt(d[5]);

		State.get(d[1]).update(d[4], State.get(d[1]).getValue(d[4]) + change);

		overall.update(d[4], overall.getValue(d[4]) + change);

		statevote.put(d[1], statevote.get(d[1]) + change);

	}

	public void topkInConstituency(String constituency, String k) {
		// write your code here
		ArrayList<String> l = topk.get(constituency).klarge(Integer.parseInt(k));
		String[] s = l.toArray(new String[l.size()]);

		for (int i = 0; i < s.length; i++) {
			String[] d = data.get(s[i]);
			System.out.println(d[0] + ", " + l.get(i) + ", " + d[4]);
		}

	}

	public void leadingPartyInState(String state) {
		// write your code here
		ArrayList<String> a = State.get(state).getLargest();
		String[] l = a.toArray(new String[a.size()]);
		Arrays.sort(l);
		for (int i = 0; i < l.length; i++) {
			System.out.println(l[i]);
		}
	}

	public void cancelVoteConstituency(String constituency) {
		// write your code here
		ArrayList<String> k = topk.get(constituency).getLevelOrder();
		topk.remove(constituency);
		String[] ll = k.toArray(new String[k.size()]);
		// int[] ll = new int[k.size()];
		// for (int i = 0; i < ll.length; i++) {
		// 	ll[i] = Integer.parseInt(l[i]);
		// }
		Arrays.sort(ll);
		// d: name, state, district, constituency, party, votes
		for (int i = 0; i < ll.length; i++) {
			String[] d = data.get((ll[i]));
			data.remove((ll[i]));
			idVote.delete((ll[i]));
			State.get(d[1]).update(d[4], State.get(d[1]).getValue(d[4]) - Integer.parseInt(d[5]));
			overall.update(d[4], overall.getValue(d[4]) - Integer.parseInt(d[5]));
			statevote.put(d[1], statevote.get(d[1]) - Integer.parseInt(d[5]));
		}

	}

	public void leadingPartyOverall() {
		// write your code here
		ArrayList<String> a = overall.getLargest();
		String[] l = a.toArray(new String[a.size()]);

		Arrays.sort(l);

		for (int i = 0; i < l.length; i++) {
			System.out.println(l[i]);
		}
	}

	public void voteShareInState(String party, String state) {
		// write your code here
		int a = State.get(state).getValue(party);
		int b = statevote.get(state);		
		System.out.println( (int) ((float) a * 100 / b ));

	}

	public void printElectionLevelOrder() {
		// write your code here
		ArrayList<String> levelOrder = idVote.getLevelOrder();
		for (int i = 0; i < levelOrder.size(); i++) {
			String candid = levelOrder.get(i);
			String[] d = data.get(candid);
			System.out.println(
					d[0] + ", " + candid + ", " + d[1] + ", " + d[2] + ", " + d[3] + ", " + d[4] + ", " + d[5]);
		}
	}
	
	public double round(double f) {

        if ((int) (f * 100 % 10) == 5) {
            if ((int) (f * 10 % 10) % 2 == 0) {
                return (double) Math.round(f * 10.0 - 1.0) / 10;
            }
        }
        return (double) Math.round(f * 10) / 10;
    }
}
