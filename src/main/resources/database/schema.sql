/**
 * Author:  bodmas
 * Created: Nov 26, 2022
 */

-- Drop table statements
drop table if exists lands_x_water_configs;
drop table if exists water_configs;
drop table if exists lands;

-- Create table statements
create table lands (
  id bigint not null auto_increment,
  created_at datetime not null,
  updated_at datetime not null,
  area double precision,
  _key varchar(127) not null,
  primary key (id)
);

create table lands_x_water_configs (
  land_id bigint not null,
  water_config_id bigint not null
);

create table water_configs (
  id bigint not null auto_increment,
  created_at datetime not null,
  updated_at datetime not null,
  _end datetime not null,
  _start datetime not null,
  water_quantity bigint not null,
  primary key (id)
);

-- Alter table statements
alter table lands add constraint UK_mdcotbkmvad2qa08jp6206rff unique (_key);
alter table lands_x_water_configs add constraint UK_lp3yioc82hpuklvxaahoqupo0 unique (water_config_id);
alter table lands_x_water_configs add constraint FKdww64ef4kjcpw57jm5v17ann6 foreign key (water_config_id) references water_configs (id);
alter table lands_x_water_configs add constraint FK66sj57y65vc6a5yowya41a36q foreign key (land_id) references lands (id);
