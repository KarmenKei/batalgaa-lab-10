# F.CSA313 — Лаборатори 10: Өгөгдлийн урсгалын тест

**Оюутан:** Ц.Бэлгүтэй  
**Оюутны код:** B232270053  
**Огноо:** 2026-11-17

## 1. Лабораторийн зорилго

Энэхүү лабораторийн ажлаар Java програмын өгөгдлийн урсгалыг шинжилж, Definition-Use (DU) хосуудад үндэслэн тест зохиов. `DataFlowTargets` классын `gcd()` болон `bonus()` функцүүдэд All-Defs болон All-DU-Pairs тестийн шалгуурыг хэрэгжүүлж, JaCoCo ашиглан branch coverage-ийг хэмжив. Мөн тестийн үр нөлөөг шалгахын тулд гурван mutation туршилт хийв.

**Анхаарах зүйл:** Лабораторийн зааврын дагуу `src/main/java/mn/edu/must/sqat/DataFlowTargets.java` файлын үндсэн кодыг өөрчлөөгүй. Mutation туршилтын үед түр өөрчлөөд, туршилт бүрийн дараа эх хувилбарыг сэргээсэн.

## 2. Орчны мэдээлэл

### Java хувилбар

```text
openjdk version "23.0.2" 2025-01-21
OpenJDK Runtime Environment Homebrew (build 23.0.2)
OpenJDK 64-Bit Server VM Homebrew (build 23.0.2, mixed mode, sharing)
```

### Maven хувилбар

```text
Apache Maven 3.9.9
Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
Java version: 23.0.2
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "14.5", arch: "aarch64", family: "mac"
```

**Төслийн тохиргоо:** Maven, JUnit 5, JaCoCo, Java 17 compile target.

## 3. A хэсэг — gcd() функцийн DU шинжилгээ

`gcd(int x, int y)` функц нь Евклидийн алгоритмаар хамгийн их ерөнхий хуваагчийг олно.

### 3.1 Definition-Use хосууд

`gcd()` функцийн DU хос бүрд definition-ээс use хүртэл дахин тодорхойлолтгүй (def-clear) замыг тэмдэглэв. Predicate use-ийн True болон False салааг тусад нь үзүүлэв.

| Хувьсагч | Def мөр | Use мөр | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| x | 13 | 16 | c-use | 13 → 15(T) → 16 | TC02 |
| x | 13 | 20 | c-use | 13 → 15(F) → 20 | TC01 |
| x | 17 | 16 | c-use | 17 → 15(T) → 16 | TC02 |
| x | 17 | 20 | c-use | 17 → 15(F) → 20 | TC02 |
| y | 13 | 15(T) | p-use | 13 → 15(T) | TC02 |
| y | 13 | 15(F) | p-use | 13 → 15(F) | TC01 |
| y | 13 | 16 | c-use | 13 → 15(T) → 16 | TC02 |
| y | 13 | 17 | c-use | 13 → 15(T) → 16 → 17 | TC02 |
| y | 18 | 15(T) | p-use | 18 → 15(T) | TC02 |
| y | 18 | 15(F) | p-use | 18 → 15(F) | TC02 |
| y | 18 | 16 | c-use | 18 → 15(T) → 16 | TC02 |
| y | 18 | 17 | c-use | 18 → 15(T) → 16 → 17 | TC02 |
| tmp | 16 | 18 | c-use | 16 → 17 → 18 | TC02 |

`TC01 — gcdZeroIterations()` нь `gcd(7, 0)` оролтоор давталтын False салааг шалгана. `TC02 — gcdTwoIterations()` нь `gcd(12, 8)` оролтоор давталтын True болон False салаа, мөн давталтын үеийн шинэ definition-үүдийн хэрэглээг шалгана.

### 3.2 Тестүүд

| Тест | Оролт | Хүлээгдэж буй үр дүн | Зорилго |
|---|---|---:|---|
| TC01 | `gcd(7, 0)` | 7 | Давталт огт ажиллахгүй |
| TC02 | `gcd(12, 8)` | 4 | Давталт олон удаа ажиллана |

Тестүүд `GcdTest.java` файлд байрлана.

## 4. B хэсэг — bonus() функцийн өгөгдлийн урсгал

### 4.1 Control Flow Graph

`bonus()` функцийн Control Flow Graph:

![bonus() Control Flow Graph](cfg-bonus.png)

CFG-ийн зангилаанууд нь эх кодын мөрийн дугаартай таарна. Шийдвэрийн зангилаануудын True/False салаалал болон давталтын буцах замыг дүрсэлсэн. Сум дээрх DU болон AD тестийн ID нь тухайн урсгалын замыг хамарч буй тестүүдийг заана.

**Тестийн ID ба тестийн method нэрсийн харгалзаа**

| Тестийн ID | Тестийн класс | Тестийн method |
|---|---|---|
| DU01 | BonusAllDUPairsTest | `inputValidation()` |
| DU02 | BonusAllDUPairsTest | `emptyAttendance()` |
| DU03 | BonusAllDUPairsTest | `streakResetAndRestart()` |
| DU04 | BonusAllDUPairsTest | `thresholdReachedWithoutFiveDayStreak()` |
| DU05 | BonusAllDUPairsTest | `fiveDayStreakBelowThreshold()` |
| DU06 | BonusAllDUPairsTest | `bothBonusesApply()` |
| DU07 | BonusAllDUPairsTest | `detectsIncorrectBestCalculation()` |
| AD01 | BonusAllDefsTest | `invalidInput()` |
| AD02 | BonusAllDefsTest | `mixedAttendance()` |
| AD03 | BonusAllDefsTest | `fiveConsecutiveDays()` |
| AD04 | BonusAllDefsTest | `emptyAttendance()` |
| AD05 | BonusAllDefsTest | `attendanceBelowThreshold()` |

Жишээлбэл, DU03 болон AD02 тестүүд нь ирцгүй өдөр тохиолдоход 46-р мөрөнд `streak = 0` болж шинэчлэгдэх замыг хамарна. Ийнхүү CFG дээрх тестийн ID-г дээрх хүснэгтээр дамжуулан бодит тестийн method нэртэй холбож болно.

### 4.2 Хувьсагчийн Definition ба Use

| Хувьсагч | Definition мөр | Use мөр | Use төрөл |
|---|---|---|---|
| attendance | 31 | 32, 38 | p-use, c-use |
| threshold | 31 | 32, 50 | p-use |
| a | 38 | 39 | p-use |
| present | 35, 40 | 40, 50 | c-use, p-use |
| streak | 36, 41, 46 | 41, 42, 43 | c-use, p-use |
| best | 37, 43 | 42, 53 | p-use |
| score | 49, 51, 54 | 54, 56 | c-use |

### 4.3 DU хосуудын хүснэгт

`Def-clear зам` нь definition-ээс use хүртэл тухайн хувьсагч дахин тодорхойлогдохгүй замыг илэрхийлнэ. Predicate use-ийн True болон False салааг тусад нь тэмдэглэв.

**Параметрүүд ба давталтын хувьсагч**

| Хувьсагч | Def | Use | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| attendance | 31 | 32(T) | p-use | 31 → 32(T) | DU01 |
| attendance | 31 | 32(F) | p-use | 31 → 32(F) | DU01, DU02–DU07, AD02–AD05 |
| attendance | 31 | 38 | c-use | 31 → 32(F) → 35–37 → 38 | DU02–DU07, AD02–AD05 |
| threshold | 31 | 32(T) | p-use | 31 → 32(T), attendance null биш үед | DU01 |
| threshold | 31 | 32(F) | p-use | 31 → 32(F), attendance null биш үед | DU01, DU02–DU07, AD02–AD05 |
| threshold | 31 | 50(T) | p-use | 31 → 32(F) → 35–49 → 50(T) | DU04, DU06, AD02–AD04 |
| threshold | 31 | 50(F) | p-use | 31 → 32(F) → 35–49 → 50(F) | DU02, DU03, DU05, DU07, AD05 |
| a | 38 | 39(T) | p-use | 38 → 39(T) | DU03–DU07, AD02, AD03, AD05 |
| a | 38 | 39(F) | p-use | 38 → 39(F) | DU03, DU07, AD02, AD05 |

**present**

| Хувьсагч | Def | Use | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| present | 35 | 40 | c-use | 35 → 38(T) → 39(T) → 40 | DU03–DU07, AD02, AD03, AD05 |
| present | 35 | 50(T) | p-use | 35 → 38(F) → 49 → 50(T) | AD04 |
| present | 35 | 50(F) | p-use | 35 → 38(F) → 49 → 50(F) | DU02 |
| present | 40 | 40 | c-use | 40 → 38(T) → 39(T) → 40 | DU03–DU07, AD02, AD03 |
| present | 40 | 50(T) | p-use | 40 → loop-ийн үлдсэн iteration-үүд → 38(F) → 50(T) | DU04, DU06, AD02, AD03 |
| present | 40 | 50(F) | p-use | 40 → loop-ийн үлдсэн iteration-үүд → 38(F) → 50(F) | DU03, DU05, DU07, AD05 |

**streak**

| Хувьсагч | Def | Use | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| streak | 36 | 41 | c-use | 36 → 38(T) → 39(T) → 40 → 41 | DU03–DU07, AD02, AD03, AD05 |
| streak | 41 | 41 | c-use | 41 → 38(T) → 39(T) → 40 → 41 | DU03–DU07, AD02, AD03 |
| streak | 41 | 42(T) | p-use | 41 → 42(T) | DU03–DU07, AD02, AD03, AD05 |
| streak | 41 | 42(F) | p-use | 41 → 42(F) | DU03, DU07, AD02 |
| streak | 41 | 43 | c-use | 41 → 42(T) → 43 | DU03–DU07, AD02, AD03, AD05 |
| streak | 46 | 41 | c-use | 46 → 38(T) → 39(T) → 40 → 41 | DU03, DU07, AD02 |

**best**

| Хувьсагч | Def | Use | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| best | 37 | 42(T) | p-use | 37 → 38(T) → 39(T) → 40 → 41 → 42(T) | DU03–DU07, AD02, AD03, AD05 |
| best | 37 | 53(F) | p-use | 37 → 38(F) → 49 → 50 → 53(F) | DU02, AD04, AD05 |
| best | 43 | 42(T) | p-use | 43 → 38(T) → 39(T) → 40 → 41 → 42(T) | DU03–DU07, AD03 |
| best | 43 | 42(F) | p-use | 43 → 38(T) → 39(F) → 46 → 38(T) → 39(T) → 40 → 41 → 42(F) | DU03, DU07, AD02 |
| best | 43 | 53(T) | p-use | 43 → loop дуусах → 49 → 50 → 53(T) | DU05, DU06, AD03 |
| best | 43 | 53(F) | p-use | 43 → loop дуусах → 49 → 50 → 53(F) | DU03, DU04, DU07, AD02, AD05 |

**score**

| Хувьсагч | Def | Use | Use төрөл | Def-clear зам | Тестийн ID |
|---|---:|---:|---|---|---|
| score | 49 | 54 | c-use | 49 → 50(F) → 53(T) → 54 | DU05 |
| score | 49 | 56 | c-use | 49 → 50(F) → 53(F) → 56 | DU02, DU03, DU07, AD05 |
| score | 51 | 54 | c-use | 51 → 53(T) → 54 | DU06, AD03 |
| score | 51 | 56 | c-use | 51 → 53(F) → 56 | DU04, AD02, AD04 |
| score | 54 | 56 | c-use | 54 → 56 | DU05, DU06, AD03 |

**Kill буюу өмнөх definition хүчингүй болох нөхцөл**

- **46-р мөр — `streak = 0;`:** Ирцгүй өдөр тохиолдоход өмнөх `streak` definition хүчингүй болж, утга 0-ээр дахин тодорхойлогдоно. Иймээс өмнөх 41-р мөрийн definition 46-р мөрөөр дамжин дараагийн iteration-ийн 42 эсвэл 43-р мөрөнд шууд хүрэх боломжгүй. Харин 46-р мөрийн шинэ definition дараагийн эерэг өдөр 41-р мөрөнд ашиглагдана. Үүнийг `DU03 — streakResetAndRestart()` болон `DU07 — detectsIncorrectBestCalculation()` шалгана.

- **51-р мөр — `score = 5;`:** Threshold хангагдсан үед 49-р мөрийн `score = 0` definition хүчингүй болж, score 5 болж дахин тодорхойлогдоно. Иймээс 49 → 54 DU хос зөвхөн 50-р мөрийн False салаагаар хүрэх боломжтой. `DU06 — bothBonusesApply()` нь 51-р мөрийн definition-ээс 54-р мөрийн use хүртэлх замыг шалгана.

**Хүрэх боломжгүй DU замын тайлбар**

- `streak`-ийн 36-р мөрийн definition-ээс 42-р мөрөнд шууд хүрэх боломжгүй. Учир нь 41-р мөрөнд `streak` дахин тодорхойлогдоно.
- 46-р мөрийн definition-ээс 42-р мөрөнд шууд хүрэх боломжгүй. Дараагийн эерэг өдөр 41-р мөрөнд `streak` дахин тодорхойлогдсоны дараа 42-р мөрөнд хүрнэ.
- `best`-ийн 37-р мөрийн definition-ээс 42-р мөрийн False салаанд хүрэх боломжгүй. Эхний эерэг өдөр `streak` 1 болж, `best` 0 байх тул 42-р мөрийн нөхцөл заавал True болно. Харин 43-р мөрөнд `best` дахин тодорхойлогдоно.
- 49 → 54 хос нь 50-р мөрийн True салаанд боломжгүй. 51-р мөрөнд score дахин тодорхойлогдож, 49-р мөрийн definition-ийг kill хийнэ.

### 4.4 All-Defs тестүүд

`BonusAllDefsTest.java` файлд дараах тестүүдийг хэрэгжүүлсэн.

| Тест | Оролт | Хүлээгдэж буй үр дүн | Хамрах зорилго |
|---|---|---:|---|
| AD01 | `null`, мөн сөрөг threshold | Exception | Оролтын шалгалт |
| AD02 | `{1,1,0,1,1}`, threshold 3 | 5 | Ирц тоолох, streak reset |
| AD03 | `{1,1,1,1,1}`, threshold 5 | 8 | Урт streak, хоёр урамшуулал |
| AD04 | `{}`, threshold 0 | 5 | Хоосон массив |
| AD05 | `{1,0}`, threshold 2 | 0 | Threshold хангахгүй |

All-Defs шалгуурын гол шаардлага нь хувьсагч бүрийн тодорхойлолт дор хаяж нэг удаа хэрэглээнд хүрэх явдал юм. Эдгээр тестүүд параметр, эхний утга оноолт, давталтын үеийн шинэ тодорхойлолт болон онооны өөрчлөлтүүдийг хамруулсан.

**All-Defs-д шаардагдах хамгийн бага тестийн тоо**

Онолын хувьд All-Defs шалгуурыг хангахад хамгийн багадаа **2 valid execution** шаардлагатай. Учир нь 49-р мөрийн `score = 0` болон 51-р мөрийн `score = 5` гэсэн хоёр definition нэг execution-д хоёулаа use-д хүрэх боломжгүй.

50-р мөрийн нөхцөл True бол 51-р мөрөнд score дахин тодорхойлогдож, 49-р мөрийн definition хүчингүй болно. Харин False бол 49-р мөрийн definition дараагийн use-д хүрнэ. Иймээс хоёр өөр execution шаардлагатай.

Жишээлбэл:

| Execution | Оролт | Хүлээгдэж буй үр дүн | Хамрах зүйл |
|---|---|---:|---|
| 1 | `{1,0,1,1,1,1,1,1}`, threshold 8 | 3 | 49-р мөрийн definition 54-р мөрийн use-д хүрнэ; streak reset болон best-ийн шинэ definition-үүдийг шалгана |
| 2 | `{1,1,1,1,1}`, threshold 5 | 8 | 51-р мөрийн definition 54-р мөрийн use-д хүрнэ |

Нэг execution хангалтгүй бөгөөд хоёр execution нь score-ийн хоёр definition-ийг хоёуланг нь use-д хүргэх доод хязгаар юм. Одоогийн `BonusAllDefsTest` дахь таван тест нь энэ доод хязгаараас илүү олон нөхцөл, оролтын хувилбарыг шалгаж байгаа хэрэг юм.

### 4.5 All-DU-Pairs тестүүд

`BonusAllDUPairsTest.java` файлд DU хосуудыг шалгах тестүүдийг хэрэгжүүлсэн.

| Тест | Оролт | Хүлээгдэж буй үр дүн | Зорилго |
|---|---|---:|---|
| DU01 | `null`, мөн сөрөг threshold | Exception | Оролтын шалгалтын салааллууд |
| DU02 | `{}`, threshold 1 | 0 | Давталтгүй зам, эхний тодорхойлолтууд |
| DU03 | `{1,0,1,1}`, threshold 5 | 0 | Streak reset болон дахин эхлэх |
| DU04 | `{1,1}`, threshold 2 | 5 | Threshold урамшуулал |
| DU05 | `{1,1,1,1,1}`, threshold 6 | 3 | Зөвхөн дараалсан ирцийн урамшуулал |
| DU06 | `{1,1,1,1,1}`, threshold 5 | 8 | Хоёр урамшуулал хоёулаа |
| DU07 | `{1,1,0,1,1,1,1}`, threshold 7 | 0 | Нийт ирц ба хамгийн урт streak-ийг ялгах |

DU07 нь M2 mutation-ийг илрүүлэх зорилгоор нэмэгдсэн. Нийт ирц 6 боловч хамгийн урт дараалсан streak 4 тул анхны функц 0 оноо буцаана.

### 4.6 All-Defs ба All-DU-Pairs-ийн ялгаа

All-Defs нь хувьсагч бүрийн тодорхойлолт хэрэглээнд хүрсэн эсэхийг шалгадаг. Харин All-DU-Pairs нь тодорхойлолт бүрээс тухайн хувьсагчийн боломжит хэрэглээ хүртэлх def-clear замуудыг хамруулахыг зорьдог. Иймээс All-DU-Pairs нь илүү нарийвчилсан шалгуур бөгөөд тодорхойлолтын дараалал, давталт, дахин оноолт, салааллыг илүү сайн шалгана.

## 5. JaCoCo coverage үр дүн

### 5.1 All-Defs

`results/jacoco-alldefs.csv`:

| Үзүүлэлт | Missed | Covered |
|---|---:|---:|
| Instructions | 3 | 78 |
| Branches | 0 | 16 |
| Lines | 1 | 23 |
| Complexity | 1 | 10 |
| Methods | 1 | 2 |

### 5.2 All-DU-Pairs ба эцсийн үр дүн

`results/jacoco-final.csv`:

| Үзүүлэлт | Missed | Covered |
|---|---:|---:|
| Instructions | 3 | 78 |
| Branches | 0 | 16 |
| Lines | 1 | 23 |
| Complexity | 1 | 10 |
| Methods | 1 | 2 |

**Branch coverage: 16/16 = 100%.**

Нэг мөрийн coverage дутуу байгаа нь `DataFlowTargets` классын implicit constructor юм. Зорилтот хоёр функцийн branch-ууд бүгд хамрагдсан.

**100% branch coverage нь бүх DU хос хамрагдсан гэсэн үг биш**

Branch coverage нь шийдвэр бүрийн True болон False үр дүнг шалгадаг боловч тухайн definition тодорхой use-д хүрсэн эсэхийг заавал баталдаггүй.

Жишээлбэл:

```java
int value = 0;

if (firstCondition) {
    value = 1;
}

if (secondCondition) {
    System.out.println(value);
}
```

Дараах хоёр тестийг авч үзье:

| Тест | firstCondition | secondCondition | Үр дүн |
|---|---|---|---|
| T1 | true | false | `value = 1` ажиллана, гэхдээ хэвлэх use-д хүрэхгүй |
| T2 | false | true | Хэвлэх use ажиллана, гэхдээ `value = 1` definition үүсээгүй |

Эдгээр тест нийлээд хоёр нөхцөлийн True болон False салааг бүгдийг хамарна. Гэсэн ч `value = 1` definition-ээс `System.out.println(value)` use хүртэлх DU хос хамрагдахгүй.

Энэ нь 100% branch coverage байсан ч All-DU-Pairs шалгуур бүрэн хангагдсан гэсэн үг биш болохыг харуулна.

## 6. C хэсэг — Mutation testing

Mutation бүрийг `DataFlowTargets.java` файлд түр хэрэгжүүлж, `mvn clean test` ажиллуулсан. Туршилтын дараа эх кодыг `git restore` ашиглан сэргээсэн.

| Mutation | Өөрчлөлт | Үр дүн | Тайлбар |
|---|---|---|---|
| M1 | 41-р мөрийн `streak = streak + 1;` мөрийг хассан | Killed | Streak тооцоолол буруу болж, тестүүд алдаа илрүүлсэн |
| M2 | 43-р мөрийн `best = streak;`-ийг `best = present;` болгосон | Killed | Эхний тестүүд илрүүлээгүй. DU07 нэмсний дараа буруу тооцооллыг илрүүлсэн |
| M3 | Давталтын дараа ашиглагдахгүй `int total = present + best;` хувьсагч нэмсэн | Survived | Програмын үр дүн өөрчлөгдөөгүй тул behavior-based тестүүд алдаа илрүүлээгүй |

### 6.1 Mutation туршилтын нотолгоо

**M1 — `streak = streak + 1;` мөрийг хассан**

`results/mvn-test-M1.txt` файлын үр дүнд нийт 13 тестээс 3 нь алдаа илрүүлсэн.

| Тестийн method | Expected | Was |
|---|---:|---:|
| `BonusAllDUPairsTest.fiveDayStreakBelowThreshold()` | 3 | 0 |
| `BonusAllDUPairsTest.bothBonusesApply()` | 8 | 5 |
| `BonusAllDefsTest.fiveConsecutiveDays()` | 8 | 5 |

41-р мөрийн streak нэмэгдүүлэх үйлдлийг хассанаар `streak` 0 хэвээр үлдэнэ. Иймээс дараалсан ирцийн урамшуулал тооцогдохгүй. Зөвхөн 5 онооны threshold урамшуулал авах ёстой тохиолдолд ч нийлбэр оноо буруу гарч байгааг тестүүд илрүүлсэн.

**M2 — `best = streak;`-ийг `best = present;` болгосон**

Эхний туршилтын `results/mvn-test-M2.txt` файлд 13 тест бүгд амжилттай болсон (`BUILD SUCCESS`). Учир нь эхний тестүүд нийт ирцийн тоо болон хамгийн урт дараалсан ирцийн ялгааг хангалттай шалгаагүй.

Энэ алдааг илрүүлэхийн тулд DU07 тест нэмсэн:

- Оролт: `{1,1,0,1,1,1,1}`, threshold `7`
- Нийт ирц: 6 өдөр
- Хамгийн урт дараалсан ирц: 4 өдөр
- Зөв хариу: 0

`best = present` гэсэн буруу тооцооллоор хамгийн урт streak-ийг 6 гэж үзэж, 3 онооны урамшуулал олгоно. `results/mvn-test-M2-kill.txt` файлд DU07 энэ mutation-ийг дараах байдлаар илрүүлсэн:

| Тестийн method | Expected | Was |
|---|---:|---:|
| `BonusAllDUPairsTest.detectsIncorrectBestCalculation()` | 0 | 3 |

Ингэснээр M2 эхний 13 тестэд илрээгүй боловч DU07 нэмсний дараа илэрсэн.

**M3 — ашиглагдахгүй локал хувьсагч нэмсэн**

`int total = present + best;` гэсэн ашиглагдахгүй хувьсагч нэмэхэд програмын буцаах утга өөрчлөгдөөгүй. Иймээс тестүүд mutation-ийг илрүүлээгүй (`Survived`). Энэ төрлийн асуудлыг IDE-ийн static analysis эсвэл code inspection ашиглан илрүүлж болно.

**Нотолгооны файлууд**

- `results/mvn-test-M1.txt` — M1-ийн 3 алдаатай тест.
- `results/mvn-test-M2.txt` — DU07 нэмэхээс өмнөх 13 тестийн амжилттай үр дүн.
- `results/mvn-test-M2-kill.txt` — DU07 нэмсний дараа M2 илэрсэн үр дүн.
- `results/mvn-test-M3.txt` — M3-ийн үр дүн.

## 7. Эцсийн тестийн үр дүн

Эцсийн `mvn clean verify` ажиллуулалтын үр дүн:

| Test class | Tests | Failures | Errors |
|---|---:|---:|---:|
| GcdTest | 2 | 0 | 0 |
| BonusAllDUPairsTest | 7 | 0 | 0 |
| BonusAllDefsTest | 5 | 0 | 0 |
| **Нийт** | **14** | **0** | **0** |

**Build status: BUILD SUCCESS**

Нотолгооны файлууд `results/` хавтсанд хадгалагдсан.

## 8. Дүгнэлт

Энэ лабораторийн ажлаар өгөгдлийн урсгалын тестийн All-Defs болон All-DU-Pairs шалгууруудыг хэрэгжүүлэв. `gcd()` функцэд хоёр тест зохиож, хувьсагчдын тодорхойлолт болон хэрэглээний хосуудыг шалгасан. `bonus()` функцийн CFG-г байгуулж, хувьсагч бүрийн definition-use хамаарлыг тодорхойлсон. All-Defs болон All-DU-Pairs тестүүдийн тусламжтайгаар оролтын шалгалт, давталт, streak reset болон онооны хоёр төрлийн урамшууллыг хамруулсан.

JaCoCo-ийн эцсийн үр дүнгээр зорилтот хоёр функцийн бүх 16 branch хамрагдаж, нийт 14 тест алдаагүй ажилласан. Mutation testing нь тестийн чанарыг үнэлэхэд чухал болохыг харуулсан бөгөөд M2 mutation-ийг илрүүлэхийн тулд DU07 тест нэмэх шаардлагатай болсон. Харин M3 шиг програмын үр дүнд нөлөөлөхгүй өөрчлөлтийг зөвхөн функциональ тестээр илрүүлэх боломжгүй тул static analysis болон code review мөн шаардлагатай.

## 9. AI ашигласан эсэх

Энэхүү лабораторийн ажлын явцад AI хэрэгслийг DU шинжилгээний ойлголт, тестийн хувилбар боловсруулах, Maven/JaCoCo үр дүнг тайлбарлах зорилгоор ашигласан. Код болон тестүүдийг локал орчинд ажиллуулж, үр дүнг өөрөө шалгав.