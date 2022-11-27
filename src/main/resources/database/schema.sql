/**
 * Author:  bodmas
 * Created: Nov 26, 2022
 */

-- Drop table statements
DROP TABLE IF EXISTS lands_x_water_configs;
DROP TABLE IF EXISTS water_configs;
DROP TABLE IF EXISTS lands;

-- Create table statements
CREATE TABLE lands (
  id bigint NOT NULL auto_increment,
  created_at datetime NOT NULL,
  updated_at datetime NOT NULL,
  area DOUBLE PRECISION,
  _key VARCHAR(127) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE lands_x_water_configs (
  land_id bigint NOT NULL,
  water_config_id bigint NOT NULL
);

CREATE TABLE water_configs (
  id bigint NOT NULL auto_increment,
  created_at datetime NOT NULL,
  updated_at datetime NOT NULL,
  _end datetime NOT NULL,
  _start datetime NOT NULL,
  water_quantity bigint NOT NULL,
  PRIMARY KEY (id)
);

-- Alter table statements
ALTER TABLE lands ADD CONSTRAINT UK_mdcotbkmvad2qa08jp6206rff UNIQUE (_key);
ALTER TABLE lands_x_water_configs ADD CONSTRAINT UK_lp3yioc82hpuklvxaahoqupo0 UNIQUE (water_config_id);
ALTER TABLE lands_x_water_configs ADD CONSTRAINT FKdww64ef4kjcpw57jm5v17ann6 FOREIGN KEY (water_config_id) REFERENCES water_configs (id);
ALTER TABLE lands_x_water_configs ADD CONSTRAINT FK66sj57y65vc6a5yowya41a36q FOREIGN KEY (land_id) REFERENCES lands (id);
