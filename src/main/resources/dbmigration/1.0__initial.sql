-- apply changes
create table survey_enterprise (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  code                          varchar(255),
  password                      varchar(255),
  ext                           longtext,
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint pk_survey_enterprise primary key (id)
);

create table survey_person (
  id                            bigint auto_increment not null,
  enterprise_id                 bigint,
  name                          varchar(255),
  code                          varchar(255),
  mobile                        varchar(255),
  ext                           longtext,
  created_at                    datetime(6),
  updated_at                    datetime(6),
  ip                            varchar(255),
  location                      varchar(255),
  constraint pk_survey_person primary key (id)
);

create table sys_manager (
  id                            bigint auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  name                          varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  admin                         integer not null,
  role_id                       bigint,
  state                         varchar(255),
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint uq_sys_manager_username unique (username),
  constraint pk_sys_manager primary key (id)
);

create table sys_menu (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  alias                         varchar(255),
  type                          varchar(255),
  icon                          varchar(255),
  parent_id                     bigint,
  link                          varchar(255),
  priority                      integer not null,
  is_show                       integer not null,
  constraint pk_sys_menu primary key (id)
);

create table sys_params (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  alias                         varchar(255),
  value                         varchar(255),
  remark                        varchar(255),
  constraint pk_sys_params primary key (id)
);

create table sys_role (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  type                          varchar(255),
  constraint pk_sys_role primary key (id)
);

create table sys_role_menu (
  id                            bigint auto_increment not null,
  role_id                       bigint,
  menu_id                       bigint,
  constraint pk_sys_role_menu primary key (id)
);

create table tmp_log (
  id                            bigint auto_increment not null,
  class_id                      varchar(255),
  class_type                    varchar(255),
  class_name                    varchar(255),
  class_level                   varchar(255),
  content                       varchar(255),
  remark                        varchar(255),
  created_at                    datetime(6),
  updated_at                    datetime(6),
  constraint pk_tmp_log primary key (id)
);

create table tmp_upload (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  path                          varchar(255),
  link                          varchar(255),
  suffix                        varchar(255),
  type                          varchar(255),
  size                          varchar(255),
  constraint pk_tmp_upload primary key (id)
);

alter table survey_person add constraint fk_survey_person_enterprise_id foreign key (enterprise_id) references survey_enterprise (id) on delete restrict on update restrict;
create index ix_survey_person_enterprise_id on survey_person (enterprise_id);

alter table sys_manager add constraint fk_sys_manager_role_id foreign key (role_id) references sys_role (id) on delete restrict on update restrict;
create index ix_sys_manager_role_id on sys_manager (role_id);

alter table sys_menu add constraint fk_sys_menu_parent_id foreign key (parent_id) references sys_menu (id) on delete restrict on update restrict;
create index ix_sys_menu_parent_id on sys_menu (parent_id);

alter table sys_role_menu add constraint fk_sys_role_menu_role_id foreign key (role_id) references sys_role (id) on delete restrict on update restrict;
create index ix_sys_role_menu_role_id on sys_role_menu (role_id);

alter table sys_role_menu add constraint fk_sys_role_menu_menu_id foreign key (menu_id) references sys_menu (id) on delete restrict on update restrict;
create index ix_sys_role_menu_menu_id on sys_role_menu (menu_id);

