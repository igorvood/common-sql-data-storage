create table jp.data_table(
  pk1_data number not null,
  constraint data_table_pk primary key (pk1_data),
  col_date timestamp not null,

  pk1 number not null,
  pk2 varchar2(100) not null,
  constraint data_table_dict_some_fk foreign key (pk1, pk2)
                          references jp.DICT_SOME(pk1, pk2)

)
/
comment on table jp.data_table is 'kjhdf @DATA(data_table_1)'
/