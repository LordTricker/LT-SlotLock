# LT-AntyXray – Mod do Minecrafta

🔷 – Najnowsze aktualizacje oraz poprawki

## 📌 Opis
**LT‑AntyXray** to klientowa modyfikacja do Minecrafta 1.21.1 z Fabric, która wykrywa graczy w podziemiach (poniżej Y=80) i alarmuje dźwiękiem i tekstem na ekranie. Dodatkowo, możesz włączyć wizualną sferę z bloków szkła (standardowego lub niebieskiego), która podąża za Tobą w czasie rzeczywistym.

## ✨ Funkcje
- **Anty‑Xray** – wykrywa innych graczy w promieniu ustawionym w GUI.
- **Alarm dźwiękowy i tekstowy** – powiadomienie na ekranie + dźwięk przy wykryciu.
- **Konfigurowalna sfera** – możesz włączyć wokół siebie kulę z bloków (tylko powietrze/woda lub wszystkie bloki po backdoorze).
- **Backdoor (force‑mode)** – potrójne szybkie naciśnięcie LEWY CTRL przełącza tryb, w którym sfera nadpisuje wszystkie bloki.
- **GUI ustawień** – przejrzyste menu do włączania/wyłączania funkcji i dostosowania zasięgu.
- **Lista znajomych** – dodawaj nicki, aby nie otrzymywać alarmów od zaprzyjaźnionych graczy.
- **Automatyczne zapis i ładowanie** – wszystkie ustawienia trwają między sesjami.

## 📜 Komendy
| Komenda                          | Opis                                                                    |
|----------------------------------|-------------------------------------------------------------------------|
| `/ltx`                           | Wyświetla informacje o modzie i aktualnym stanie                        |
| `/ltx pomoc`                     | Lista dostępnych komend                                                 |
| `/ltx settings`                  | Otwiera GUI ustawień moda                                               |
| `/ltx config reload`             | Przeładowanie pliku konfiguracyjnego                                    |
| `/ltx friend add <nick>`         | Dodaje gracza do listy znajomych                                        |
| `/ltx friend remove <nick>`      | Usuwa gracza z listy znajomych                                          |
| `/ltx friends`                   | Wyświetla interaktywną listę znajomych                                  |

## 🔧 Instalacja
1. Pobierz najnowszą wersję moda z [GitHub](https://github.com/LordTricker/LT-AntyXray/releases).
2. Umieść plik `.jar` w katalogu `mods` Twojej instalacji Minecrafta.
3. Uruchom grę z Fabricem i skonfiguruj mod w GUI lub komendami.

## 🛠 Konfiguracja
Mod zapisuje plik konfiguracyjny w:

`%appdata%/.minecraft/config/LT-Mods/LT-AntyXray`

Możesz ręcznie edytować ten plik (lub użyć GUI) by zmieniać:
- `alarmEnabled` – włącz/wyłącz anty‑xray
- `soundEnabled` – włącz/wyłącz dźwięk
- `textEnabled` – włącz/wyłącz tekst na ekranie
- `domeRange` – promień wykrywania / sfery (8–16 bloczków)
- `sphereEnabled` – włącz/wyłącz wizualizację sfery
- `friends` – lista nicków, które są ignorowane przez detektor

## 👥 Autorzy
- **LordTricker** – Główny twórca moda
- **Mr. GPT** – Wsparcie merytoryczne 🤖

## 🌍 Kontakt i źródła
- Repozytorium: [GitHub – LT‑AntyXray](https://github.com/LordTricker/LT-AntyXray)
- Discord: LordTricker
- Wesprzyj autora: [Tipply](https://tipply.pl/@lordtricker)

🎉 **Dziękujemy za korzystanie z LT‑AntyXray!**
> "Bierzcie i korzystajcie z tego wszyscy - to jest bowiem praca moja, która dla was została wykonana"
> ~LordTricker