package com.example.example_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {
    //interface

    public interface OnLongItemClickListener{
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener;
    //setter
    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this.onLongItemClickListener = (OnLongItemClickListener) onLongItemClickListener;
    }


    private List<Person> personList;
    private PersonListener listener;
    //연결 시키기위해 setter기능 필요
    public void setListener(PersonListener listener){
        this.listener = listener;
    }

    public PersonAdapter(List<Person> personList){this.personList = personList;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view,listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.tvname.setText(person.getName());
        holder.tvphone.setText(person.getPhone());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), person.getName(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(v.getContext(), person.getPhone(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });


       

    }

    @Override
    public int getItemCount() {

        return (null != personList ? personList.size():0);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvname;
        private TextView tvphone;

        public MyViewHolder(@NonNull View itemView, final PersonListener personListener){
            super(itemView);
            tvname = itemView.findViewById(R.id.tvname);
            tvphone = itemView.findViewById(R.id.tvphone);
            //외부 인터페이스에서 만들어서 사용
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position = getAdapterPosition();
                   listener.onItemClick(position);

                }
            });

            //내부에서 만들어봄
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    onLongItemClickListener.onLongItemClick(position);
                    return true;
                }
            });
        }

    }
    //전체보기
    public void listItem(List<Person> personList){
        this.personList = personList;
        notifyDataSetChanged();
    }
    //추가
    public void addItem(Person person){
        personList.add(person);
        notifyDataSetChanged();
    }

    //getItem
    public Person getItem(int position){
       Person person = personList.get(position);
       return person;
    }

    //update
    public void updateItem(Person person, int position){
        Person p = personList.get(position);
        p.setName(person.getName());
        p.setPhone(person.getPhone());
        notifyDataSetChanged();
    }

    //delete
    public void removeItem(int position){
        personList.remove(position);
        notifyDataSetChanged();
    }
}
