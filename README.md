# 待办清单 App（Android · Jetpack Compose）

根据 `待办清单App-设计规格.md` 实现的高保真 Android 客户端。视觉严格遵循设计稿：暖奶油底（`#F8F6F2`）+ 鼠尾草绿（`#6BA8A0`），Soft Nordic 质感。

## 技术栈
- **Kotlin 1.9.24 + Jetpack Compose**（BOM 2024.09.00）
- **Material 3** 主题 + 自定义 Design Token
- **Room**（本地持久化）+ **Kotlin Flow** + **ViewModel**
- **Navigation Compose**（底部 4 tab + 详情页路由）
- 编译要求：AGP 8.5.2 / Gradle 8.9 / **JDK 17**

## 目录结构
```
app/src/main/
├─ AndroidManifest.xml
├─ res/values/{themes,colors,strings}.xml, res/drawable/ic_launcher.xml
└─ java/com/example/todolist/
   ├─ ToDoApplication.kt / MainActivity.kt
   ├─ data/
   │  ├─ model/{Todo,CheckIn}.kt
   │  ├─ local/{AppDatabase,TodoDao,CheckInDao}.kt
   │  └─ repository/TodoRepository.kt
   ├─ ui/
   │  ├─ theme/{Color,Type,Tokens,Theme}.kt      # 设计 Token
   │  ├─ components/{Components,TopBars,BottomNav,Inputs}.kt
   │  ├─ navigation/AppNavHost.kt                # 路由 + 全局 Repository
   │  ├─ home/        # 首页（Screen 1）
   │  ├─ editor/      # 新建/编辑（Screen 2）
   │  ├─ reminder/    # 提醒设置（Screen 3）
   │  ├─ ringtone/    # 提醒铃声（Screen 4）
   │  ├─ stats/       # 打卡统计 + 热力图（Screen 5）
   │  ├─ reminders/   # 提醒 tab 列表
   │  └─ mine/        # 我的 tab
   └─ util/{DateUtils,SampleData}.kt
```

## 如何运行
1. 用 **Android Studio（Hedgehog 或更新版本，自带 JDK 17）** 打开本工程根目录 `paly/`。
2. 首次导入若缺少 Gradle Wrapper，Android Studio 会自动生成；或本地装好 Gradle 8.9 后执行 `gradle wrapper`。
3. 连接设备或启动模拟器（API ≥ 24），点击 ▶ Run。
4. 首次启动会自动写入种子数据（阅读30分钟 / 健身打卡 / 背单词50个，以及近 70 天打卡记录）。

> ⚠️ 本交付环境**未安装 Android SDK / Gradle**，因此未执行实际编译。代码已按可编译结构组织，请在本地 Android Studio 中验证与打包。

## 已实现的页面与交互
| 页面 | 要点 |
|---|---|
| 待办列表（Screen 1） | 日期/标题 Header、今日打卡进度卡（百分比+进度条+已勾选 X/Y）、事项列表（勾选框/进度/铃铛）、底部 4 tab |
| 新建/编辑（Screen 2） | 名称、总天数/已打卡天数并排输入、实时进度预览、计入今日开关、提醒设置入口、保存主按钮 |
| 提醒设置（Screen 3） | 总开关、定时提醒（时:分:秒 精确到秒，默认 14:00:23）、重复（每天/工作日/周末/自定义）、间隔提醒（开始时间+间隔值+分钟/小时）、铃声入口 |
| 提醒铃声（Screen 4） | 当前选中预览+播放、系统铃声单选（水滴声/鸟鸣/清脆/柔和）、在线搜歌（搜索框+结果行+下载）、使用选中铃声 |
| 打卡统计（Screen 5） | 连续打卡 / 本月打卡大数字、GitHub 风格 10×7 热力图（5 级色阶）、少→多图例 |

关键规则均落地：进度 `= 已打卡/总天数`；仅勾选且「计入今日」开启才计入当日；热力图与统计由勾选数据聚合得出（连续打卡=7，自动对齐最后 7 天）。

## 待接入 / 已知限制（设计稿 §6 范围的工程实现点）
1. **字体**：设计稿要求 Noto Sans SC / Inter。当前用系统默认字体以保证零资源可编译；将 `noto_sans_sc.ttf` / `inter.ttf` 放入 `res/font/` 后，在 `ui/theme/Type.kt` 把 `FontFamily.Default` 替换为对应 `FontFamily` 即可（文件内已注释）。
2. **搜歌 API**（§4.5）：当前为演示占位（返回与关键词相关的示例结果）。替换为开源免费、无需 token 的 API（如 Deezer / iTunes Search）仅需改 `RingtoneViewModel.setQuery()`。
3. **实际提醒调度**：开关/时间/重复/间隔数据已建模并持久化，但未接 `AlarmManager` / `WorkManager` 真正触发系统通知。
4. **铃声播放/下载**：预览播放与「下载」为演示态，未接 `MediaPlayer` 与真实音频文件落地。
5. **状态栏**：采用系统状态栏（主题奶油底+深色图标），未额外绘制设计稿中的 faux 状态栏，以符合 Android 规范。
