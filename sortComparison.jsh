import java.io.*;
import java.util.*;

ArrayList<Integer> cardCompare(String card1, String card2) {
    int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));
    char suit1 = card1.charAt(card1.length() - 1);
    char suit2 = card2.charAt(card2.length() - 1);

    String suitPriority = "HCDS";

    if (suitPriority.indexOf(suit1) < suitPriority.indexOf(suit2)) {
        return new ArrayList<>(List.of(-1));
    } else if (suitPriority.indexOf(suit1) > suitPriority.indexOf(suit2)) {
        return new ArrayList<>(List.of(1));
    } else {
        if (number1 < number2) {
            return new ArrayList<>(List.of(-1));
        } else if (number1 > number2) {
            return new ArrayList<>(List.of(1));
        } else {
            return new ArrayList<>(List.of(0));
        }
    }
}

ArrayList<String> bubbleSort(ArrayList<String> array) {
    int n = array.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (cardCompare(array.get(j), array.get(j + 1)).get(0) > 0) {
                String temp = array.get(j);
                array.set(j, array.get(j + 1));
                array.set(j + 1, temp);
            }
        }
    }
    return array;
}

ArrayList<String> mergeSort(ArrayList<String> array) {
    if (array.size() <= 1) {
        return array;
    }

    int mid = array.size() / 2;
    ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
    ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));

    left = mergeSort(left);
    right = mergeSort(right);

    return merge(left, right);
}

ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
    ArrayList<String> result = new ArrayList<>();
    int i = 0, j = 0;

    while (i < left.size() && j < right.size()) {
        if (cardCompare(left.get(i), right.get(j)).get(0) <= 0) {
            result.add(left.get(i));
            i++;
        } else {
            result.add(right.get(j));
            j++;
        }
    }

    while (i < left.size()) {
        result.add(left.get(i));
        i++;
    }

    while (j < right.size()) {
        result.add(right.get(j));
        j++;
    }

    return result;
}

long measureBubbleSort(String filename) {
    ArrayList<String> list = new ArrayList<>();
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line.trim());
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    long startTime = System.currentTimeMillis();
    bubbleSort(list);
    long endTime = System.currentTimeMillis();

    return endTime - startTime;
}

long measureMergeSort(String filename) {
    ArrayList<String> list = new ArrayList<>();
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line.trim());
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    long startTime = System.currentTimeMillis();
    mergeSort(list);
    long endTime = System.currentTimeMillis();

    return endTime - startTime;
}

void sortComparison(String[] filenames) {
    BufferedWriter writer = null;
    try {
        writer = new BufferedWriter(new FileWriter("sortComparison.csv"));
        writer.write(", ");
        for (String filename : filenames) {
            int lineCount = 0;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(filename));
                while (reader.readLine() != null) {
                    lineCount++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            writer.write(lineCount + ", ");
        }
        writer.newLine();

        writer.write("bubbleSort, ");
        for (String filename : filenames) {
            long bubbleTime = measureBubbleSort(filename);
            writer.write(bubbleTime + ", ");
        }
        writer.newLine();

        writer.write("mergeSort, ");
        for (String filename : filenames) {
            long mergeTime = measureMergeSort(filename);
            writer.write(mergeTime + ", ");
        }
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
