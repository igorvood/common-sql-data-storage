create table jp.dict_some(
  pk1 number not null,
  pk2 varchar2(100) not null,
  constraint dict_some_pk primary key (pk1, pk2),
  col_date timestamp not null
)
/
comment on table jp.dict_some is 'kjhdf @DICT'
/