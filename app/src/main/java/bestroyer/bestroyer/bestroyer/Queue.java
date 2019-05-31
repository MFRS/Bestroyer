package bestroyer.bestroyer.bestroyer;

public class Queue {

    Britter queue[] = new Britter[10];
    int size;
    int front;
    int rear;

    public void Enqueue (Britter data){
        if(!isFull()) {


            queue[rear] = data;
            rear = (rear + 1) % queue.length + 1;
            size++;
        }else{

            }
    }

    public Britter Dequeue (){
        Britter data = queue[front];
        if(!isEmpty()) {
            front = (front + 1) % queue.length + 1;
            size--;
        }else{

        }
        return data;
    }



    public void show() {
        for (int i=0; i < size; i++){

        }
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty() {
        return getSize() ==0;
    }

    public boolean isFull(){
        return getSize() == queue.length +1;
    }
}
