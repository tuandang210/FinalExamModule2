import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manage {
    List<User> users = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void menu() throws Exception {
        int choice = -1;

        do {
            System.out.println("---- CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ----");
            System.out.println("Chọn chức năng theo số (để tiếp tục):");
            System.out.println("1: Xem danh sách người dùng");
            System.out.println("2: Thêm mới người dùng");
            System.out.println("3: Cập nhật người dùng");
            System.out.println("4: Xóa người dùng");
            System.out.println("5: Tìm kiếm người dùng");
            System.out.println("6: Đọc từ file");
            System.out.println("7: Ghi ra file");
            System.out.println("8: Thoát");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayUser();
                    break;
                case 2:
                    users.add(inputUserInformation());
                    break;
                case 3:
                    replaceInformation();
                    break;
                case 4:
                    deleteInformation();
                    break;
                case 5:
                    showInformationByPhoneNumber();
                    break;
                case 6:
                    readFile();
                    break;
                case 7:
                    saveDataToFile();
                    break;
            }
        } while (choice != 8);
    }

    private void displayUser() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void readFile() {
        try {
            String content = readFromFile();
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromFile() throws Exception {
        File file = new File("data\\contacts.csv");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content = content.concat(line);
            content = content.concat("\n");
        }
        return content;
    }

    public void saveDataToFile() throws Exception {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/contacts.csv"));
            String[] str;
            for (User x : users) {
                str = x.covertToString().split(",");
                for (String s : str) {
                    bufferedWriter.write(s + ",");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    public void showInformationByPhoneNumber() {
        int check = checkPhoneNumber();
        if (check != -1) {
            System.out.println(users.get(check));
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên");
            showInformationByPhoneNumber();
        }
    }

    public void deleteInformation() throws Exception {
        int check = checkPhoneNumber();
        if (check != -1) {
            System.out.println("Nhập Y để xóa");
            String y = sc.nextLine();
            if (y.equals("Y")) {
                users.remove(check);
            } else {
                menu();
            }
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên");
            deleteInformation();
        }
    }

    public void replaceInformation() {
        int check = checkPhoneNumber();
        if (check != -1) {
            System.out.println("Đổi mới thông tin người dùng");
            System.out.println("Đổi mới tên");
            users.get(check).setName(sc.nextLine());
            System.out.println("Đổi mới ngày sinh");
            users.get(check).setDateOfBirth(sc.nextLine());
            System.out.println("Đổi mới giới tính");
            users.get(check).setGender(sc.nextLine());
            System.out.println("Đổi mới địa chỉ");
            users.get(check).setAddress(sc.nextLine());
            System.out.println("Đổi mới email");
            users.get(check).setEmail(sc.nextLine());
            System.out.println("Đổi mới group");
            users.get(check).setGroup(sc.nextLine());
            System.out.println("Success!");
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên");
            replaceInformation();
        }
    }

    public int checkPhoneNumber() {
        int index = -1;
        System.out.println("Nhập số điện thoại cần sửa thông tin: ");
        sc.nextLine();
        String phone = sc.nextLine();
        for (int i = 0; i < users.size(); i++) {
            if (phone.equals(users.get(i).getPhoneNumber())) {
                return i;
            }
        }
        return index;
    }

    public User inputUserInformation() {
        System.out.println("Nhập thông tin người dùng");
        System.out.println("Nhập tên: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Nhập ngày sinh: ");
        String dateOfBirth = sc.nextLine();
        System.out.println("Nhập giới tính: ");
        String gender = sc.nextLine();
        String phoneNumber = regexPhone();
        System.out.println("Nhập địa chỉ: ");
        String address = sc.nextLine();
        String email = regexEmail();
        System.out.println("Nhập group: ");
        String group = sc.nextLine();
        return new User(name, dateOfBirth, gender, phoneNumber, address, email, group);
    }

    public String regexEmail() {
        System.out.println("Nhập email: ");
        String email = sc.nextLine();
        if (email.matches("^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$")) {
            return email;
        } else {
            return regexEmail();
        }
    }

    public String regexPhone() {
        System.out.println("Nhập số điện thoại: ");
        String phoneNumber = sc.nextLine();
        if (phoneNumber.matches("\\d{10}")) {
            return phoneNumber;
        } else {
            return regexPhone();
        }
    }
}
