package vn.edu.hust.studentman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<MutableList<StudentModel>>(mutableListOf(
        StudentModel("Mai Minh Khôi", "001"),
        StudentModel("Bùi Nguyễn Xuân Thành", "002"),
        StudentModel("Bùi Mạnh Dũng", "003"),
        StudentModel("Phạm Đức Lưu", "004"),
        StudentModel("Phạm Thái Châu", "005"),
        StudentModel("Phạm Hải Nam", "006"),
        StudentModel("Trịnh Châu", "007"),
        StudentModel("Phạm Thị Phương Hoa", "008"),
        StudentModel("Đinh Văn Luận", "009"),
        StudentModel("Nguyễn Văn Tuyến", "010"),
        StudentModel("Ninh Văn Vươngg", "011"),
        StudentModel("Nguyễn Quang Thịnh", "012"),
        StudentModel("Phan Văn Đức", "013"),
        StudentModel("Nguyễn Công Phượng", "014"),
        StudentModel("Tà Phạm Thuật", "015"),
        StudentModel("Nguyễn Phúc Mạnh", "016"),
        StudentModel("Nguyễn Đức Dương", "017"),
        StudentModel("Trần Quang Thắng", "018"),
        StudentModel("Vũ Trung Kiên", "019"),
        StudentModel("Trần Xuân Thuận", "020")


    ))
    val students: LiveData<MutableList<StudentModel>> get() = _students

    fun addStudent(student: StudentModel) {
        _students.value?.add(student)
        _students.value = _students.value // Trigger LiveData update
    }

    fun updateStudent(oldId: String, newName: String, newId: String) {
        _students.value?.find { it.studentId == oldId }?.let {
            it.studentName = newName
            it.studentId = newId
            _students.value = _students.value // Trigger LiveData update
        }
    }

    fun deleteStudent(student: StudentModel) {
        _students.value?.remove(student)
        _students.value = _students.value // Trigger LiveData update
    }

    fun getStudentById(id: String?): StudentModel? {
        return _students.value?.find { it.studentId == id }
    }
}