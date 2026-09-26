# Kayji-JoinMessage

Plugin Minecraft (Spigot) **thông báo vào/ra server**: broadcast khi join/leave, title chào mừng, tin nhắn nhiều dòng và tin tức server — tất cả tùy chỉnh trong `config.yml`.

> **Tác giả:** Kayji_Tizi · **Phiên bản:** 1.0.0 · **API:** 1.16 · **Java:** 17

## Tính năng

- **Broadcast join/leave** với mẫu màu tùy chỉnh: `&a[+] &e%player% &ađã tham gia máy chủ!`
- **Title + subtitle chào mừng** khi vào server (`welcome-title`).
- **Tin nhắn nhiều dòng** (`welcome-message.lines`) gửi riêng cho người vừa vào.
- **Tin tức server** (`server-news.lines`) — banner thông báo nhiều dòng.
- **Mỗi mục bật/tắt riêng** bằng `enabled`, dùng placeholder `%player%` và mã màu `&`.
- Lệnh reload không cần restart.

## Bảng lệnh

Gõ trong game với dấu `/`:

| Lệnh | Quyền | Mô tả |
| --- | --- | --- |
| `/joinmessage reload` | `joinmessage.reload` | Tải lại `config.yml` |

> Quyền `joinmessage.reload` — mặc định chỉ `op`.

## Cấu hình

```yaml
join-broadcast:
  enabled: true
  message: '&a[+] &e%player% &ađã tham gia máy chủ!'

leave-broadcast:
  enabled: true
  message: '&c[-] &e%player% &cđã rời khỏi máy chủ.'

welcome-title:
  enabled: true
  title: '&aChào mừng!'
  subtitle: '&e%player% &avừa tham gia.'

welcome-message:
  enabled: true
  lines:
    - '&aChào mừng quay trở lại, &e%player%&a!'
    - '&7Chúc bạn có những trải nghiệm tuyệt vời!'

server-news:
  enabled: true
  lines:
    - '&6~ &eTin tức mới nhất của máy chủ:'
    - '&7- &fDùng &b/kit &fđể nhận quà mới mỗi ngày.'
```

> Dùng mã màu `&` (vd. `&6`, `&a`) và placeholder `%player%` trong mọi nội dung.

## Cài đặt

```bash
mvn clean package
```

Copy `target/Kayji-JoinMessage-2.0-beta.jar` vào thư mục `plugins/` rồi restart server.

> Maven Shade Plugin đóng gói jar (loại trừ `spigot-api`).

## Cấu trúc dự án

```
├── pom.xml                                    Maven + Shade (Java 17)
└── src/main
    ├── java/com/aefamily/joinmessage
    │   └── JoinMessagePlugin.java             Sự kiện join/leave + /joinmessage reload
    └── resources
        ├── plugin.yml                         Lệnh + quyền
        └── config.yml                         Broadcast, title, welcome, tin tức
```

## Giấy phép

[GNU General Public License v3.0](LICENSE)
