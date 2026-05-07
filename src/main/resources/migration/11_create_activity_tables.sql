do $$
    begin
        if not exists (select 1 from pg_type where typname = 'attendance_status') then
            create type attendance_status as enum (
                'ATTENDED',
                'MISSING',
                'UNDEFINED');
        end if;
    end $$;

create table if not exists activity (
    id              varchar primary key,
    collectivity_id varchar not null references collectivity(id) on delete cascade,
    label           varchar not null,
    activity_type   varchar not null
        constraint chk_activity_type check (activity_type in ('MEETING', 'TRAINING', 'OTHER')),
    executive_date  date,
    week_ordinal    integer constraint chk_week_range check (week_ordinal >= 1 and week_ordinal <= 5),
    day_of_week     varchar(2) constraint chk_day_of_week check (day_of_week in ('MO', 'TU', 'WE', 'TH', 'FR', 'SA', 'SU')),
    constraint chk_date_or_recurrence check (
        (executive_date is not null and week_ordinal is null and day_of_week is null) or
        (executive_date is null and week_ordinal is not null and day_of_week is not null))
);

create table if not exists activity_member_attendance (
    id                varchar primary key,
    activity_id       varchar not null references activity(id) on delete cascade,
    member_id         varchar not null references member(id) on delete cascade,
    attendance_status attendance_status not null default 'UNDEFINED',
    unique(activity_id, member_id)
);

create index idx_activity_collectivity on activity(collectivity_id);
create index idx_activity_date on activity(executive_date);
create index idx_activity_recurrence on activity(week_ordinal, day_of_week);
create index idx_attendance_member on activity_member_attendance(member_id);

alter table "activity"
    add column if not exists "member_occupation_concerned" member_occupation[] default '{}';