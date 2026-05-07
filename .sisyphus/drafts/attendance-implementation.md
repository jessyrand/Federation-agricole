# Implementation Draft: Activity Attendance Endpoint

## Goal
Implement the `POST /collectivities/{id}/activities/{activityId}/attendance` endpoint to record member attendance for specific activities.

## Technical Specifications

### 1. Endpoint Definition
- **Path**: `/collectivities/{id}/activities/{activityId}/attendance`
- **Method**: `POST`
- **Input**: 
    - `id` (Path): Collectivity ID
    - `activityId` (Path): Activity ID
    - `attendanceList` (Body): `List<CreateActivityMemberAttendance>`
- **Output**: `201 Created` with `List<ActivityMemberAttendance>`

### 2. Business Rules
- **Existence Checks**:
    - Collectivity must exist.
    - Activity must exist.
    - Each Member in the list must exist.
- **Immutability Rule**:
    - If current status is `ATTENDED` or `MISSING`, it cannot be changed.
    - Only `UNDEFINED` status can be updated.
- **Response**: Return the list of created/updated attendance records.

### 3. Proposed Architecture

#### Controller (`EventController`)
- Fix the `submitAttendance` method signature.
- Call `EventService.submitAttendance(...)`.
- Map service results to `ResponseEntity`.

#### Service (`EventService` - NEW)
- Orchestrate the flow:
    1. Validate Collectivity and Activity existence.
    2. For each attendance entry:
        - Validate Member existence.
        - Check current status via `ActivityRepository`.
        - Enforce immutability rule.
        - Call Repository to save.
- Handle transactions (if applicable given the raw JDBC setup).

#### Repository (`ActivityRepository`)
- Complete `insertAttendance(String activityId, String memberId, String status)`.
- Ensure it handles `INSERT ... ON CONFLICT` or check-then-insert logic.

## Success Criteria
- [ ] Endpoint returns 201 on success.
- [ ] Endpoint returns 404 if Collectivity or Activity is missing.
- [ ] Endpoint returns 400 if Member is missing or immutability rule is violated.
- [ ] Database is correctly updated.
