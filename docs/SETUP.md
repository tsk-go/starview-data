# The whole build: StarPilot, StarView and everything on the USB hub

This is how one 2021 Toyota Sienna (4th generation, hybrid) is set up: StarPilot on a comma four, a tablet as the
main screen, and a handful of extras that all hang off one USB hub. It describes what each part is, how it's
connected and set up, and what we learned getting it to work. The car-specific parts (door locks, key fob, wiring)
were only ever tested on this Sienna.

> **Read first.** openpilot and its forks are driver assistance: you stay responsible for the car at all times. Some
> of this wires into the car's own switches (turn signals, door key switch) and sends diagnostic commands to the car's
> body module. Do any of it only if you understand it and accept the risk. StarView installs its own updates as soon
> as they're published, also while driving (Settings › Tablet › About turns that off).

**One rule shaped every decision:** the comma drives, and everything else should cost it as little as possible (CPU,
GPU, memory, storage, USB bandwidth). Anything heavy (maps, object detection, voice, RTK corrections, settings
screens) runs on the tablet; the comma only shares its data.

## Parts

| Part | What it's for |
| --- | --- |
| comma four | runs StarPilot, from [tsk-go/openpilot](https://github.com/tsk-go/openpilot) branch `Dom` |
| Chestnut eGPU | external AMD GPU over USB that runs StarPilot's big driving model |
| [RSHTECH 7-port powered USB-C 3.2 hub (RSH-H7C)](https://www.amazon.com/dp/B0GTYVWT4R) | 7 USB-C ports, 10 Gbps; everything below plugs into it |
| [Czepa 12 V car DC power cord](https://www.amazon.com/dp/B091PM893V) | powers the hub from one of the car's 12 V outlets (only live while the car is on) |
| Tablet: Lenovo Idea Tab Pro (Android 16) | the main screen, running StarView (this repo) |
| USB stick, SanDisk ~460 GB | archive of finished drives |
| [SparkFun GPS-RTK Dead Reckoning Kit (SMA), u-blox ZED-F9R](https://www.amazon.com/dp/B0CQ745H6Z) | centimetre GPS with RTK corrections (comes with a multi-band magnetic antenna and a USB cable) |
| [Waveshare Raspberry Pi Pico W, pre-soldered](https://www.amazon.com/dp/B0BN14J26Q) | the relay box's brain (MicroPython; Wi-Fi not used) |
| [Hosyond 4-channel 5 V relay module with optocoupler](https://www.amazon.com/dp/B0C2Z8JF5R) | presses the turn-signal switch and the door key switch for the comma |
| [Cable Matters USB-C to Micro-USB cable, 6.6 ft](https://www.amazon.com/dp/B00UUBRYS0) | Pico to the hub |
| [Nooelec RTL-SDR v5 bundle (NESDR Smart, RTL2832U / R820T2)](https://www.amazon.com/dp/B01GDN1T4S) | hears the key fob while the car is running |
| [RED WOLF fabric wiring-harness tape](https://www.amazon.com/dp/B0BBV3NNTM) | wrapping the new wires so they're neat, protected and don't rattle |
| Android phone | internet (hotspot) and the StarLock app (lock / unlock) |

## The software

- **comma:** tsk-go/openpilot `Dom` is firestar's StarPilot (`Dom` branch) plus our patches. Every patch and why it
  exists is listed in that repo's `CLAUDE.md` ("Fork history"). It's installed like any openpilot fork
  (installer URL `installer.comma.ai/tsk-go/Dom`) and updated through the device's own Software update. The patches
  include **starviewd**, the bridge the tablet and the phone talk to (`starpilot/system/starview/`).
- **Tablet:** StarView, the Android app in `app/` here. Install the first APK from this repo's Releases page; after
  that it updates itself.
- **Phone:** StarLock, the lock / unlock app (`app/starlock`, releases named `starlock-v…`).

## 1. USB hub and power

The comma's USB-C port goes to the hub, and every device below plugs into the hub. The hub has its own power: a 12 V
car cord (Czepa) in one of the car's 12 V outlets, which are only live while the car is on.

**Car off.** With its own supply dead, the hub was fed 5 V back through the comma's cable: tablet charging, the GPS,
the radio and the Pico all ran off the car battery until the comma's low-voltage cutoff, and the comma kept
restarting. Now the comma switches its own USB port's 5 V (the power chip's OTG regulator): on as soon as the car
starts, off a set time after it turns off (default 5 minutes, 1 minute to 5 hours), and at once when the battery runs
low. The setting is in StarView › Device › System › **Power management**, on the same page as StarPilot's own
Parked Power settings. While the port is off, door activity on the car's CAN wakes it early.

**Lessons**
- The comma's GPS dropped from ~10 satellites to ~3 whenever the tablet was plugged in. Cables and hub were fine;
  replacing the hub's power supply fixed it (electrical noise from the old supply).
- Keep the USB stick off the port next to the eGPU: on a shared hub the stick kept dropping off the bus.

## 2. The tablet (StarView)

**Connection.** The tablet talks to the comma over its USB cable, using the tablet's own USB tethering (RNDIS). The
comma's kernel supports RNDIS out of the box (a phone's tethering didn't work, the tablet's did), at about
200 Mbit/s. So that the comma's internet stays on Wi-Fi, pin the cable connection as never-default on the comma:

```
sudo nmcli connection modify "Wired connection 1" ipv4.never-default yes ipv6.never-default yes
```

**Automatic tethering.** Android drops the cable to charging-only whenever it's replugged or its power comes back.
The comma turns tethering back on over adb, so it needs adb access to the tablet once: on the tablet, turn on
Developer options › USB debugging, then on the comma run `HOME=/data/starview/adbhome adb devices` and accept the
prompt on the tablet ("always allow"). If the link still doesn't come up, the comma resets the tablet's USB like a
replug. Off with `/data/starview/no_auto_tether`.

**Pairing.** In StarView, "Pair with your comma" and scan the QR code from the comma's screen, or over SSH:
`python3 -m openpilot.starpilot.system.starview.pair`. The phone's StarLock uses the same pairing.

**What the comma does for the tablet:**
- Car on: wakes the tablet and opens StarView, unless StarView is already in front (so it never closes a StarView
  screen you have open). Car off: tablet screen off after 30 s.
- While the tablet shows the driving screen, the comma's own screen goes quiet ("tablet mode").
- Battery saver (a tile in StarView's Quick menu): over adb, other apps are stopped and kept from running in the background,
  Wi-Fi and Bluetooth scanning are switched off, and StarView stays on the "don't optimize" list. Switching it off puts
  every setting back.

**Automatic updates.** StarView checks this repo's Releases once a minute and installs a new version by itself. If
it was on screen it comes back on screen; if another app was in front, it comes back behind it. To let it open again
after updating itself, it needs "display over other apps", which the comma can switch on:

```
export HOME=/data/starview/adbhome
adb -d shell appops set com.starpilot.starview SYSTEM_ALERT_WINDOW allow
```

(or the Allow button in StarView › Settings › Tablet › About). How it works is in `docs/STARVIEW-APP.md`.

**What's in StarView**, in short: the driving screen as movable, resizable cards (screen editor: hold the road view);
camera view or a "Scene" view drawn from an object detector that runs on the tablet; navigation (StarPilot's Mapbox
routing, with a turn card, a see-through map and full-screen routes, search and favourites); a lane tracker ("lane N
of M"); automatic turn signals and lane-change prompts; offline street maps and US road lane data
(HPMS, release `hpms-2024` here) stored on the tablet; spoken alerts with the Piper "Amy" voice; Toyota diagnostics
(TechStream: trouble codes, live data, hidden settings); all of StarPilot's Galaxy settings drawn natively; and the
pages for the hardware below.

## 3. Drive archive on a USB stick

Format the stick ext4 with the label `EXTDATA` (on a PC: `mkfs.ext4 -L EXTDATA /dev/sdX1`) and plug it into the hub.

openpilot keeps recording to the comma's internal storage as usual. While the car is parked, starviewd copies
finished drives to the stick every few minutes (mounting it only for the copy), at most 20 MB/s, and stops within
1 MB when the car starts. It deletes the stick's oldest drives to keep about 10 % free. Progress and space are in
StarView › Settings › Tablet › **Storage**. Off with `/data/starview/no_log_archive`.

**Lesson:** the first version mounted the stick in place of the internal log folder so openpilot recorded straight
onto it. Whenever the stick paused (busy or hot), recording stalled and openpilot showed take-over alerts. Copying
while parked has none of that.

## 4. RTK GPS (ZED-F9R)

The SparkFun kit plugs into the hub, with the magnetic antenna on the roof. starviewd reads it, and the tablet shows
it under StarView › Settings › Tablet › **RTK GPS** (fix, accuracy, satellites, sensor fusion and tools). It feeds the
tablet's lane locator and map arrow 10 times a second. It also stands in for navigation (StarPilot's "phone GPS"
input) while the comma's own GPS and the car's have no fix, which can take minutes after a reboot. openpilot's own
localization doesn't use it.

**Firmware.** The module shipped with HPS 1.00, on which the IMU never started (sensor fusion "initializing"
forever). It was flashed to HPS 1.30 with u-blox's `ubxfwupdate` (u-center's updater failed). What worked: the board
in its safe boot mode, replugged before each try, slow settings: `-s 1 -t 1 --max-num 1 -b 9600:9600:9600`.

**Receiver settings** (StarView writes and checks these itself; listed so you know what matters):
- automotive dynamic model, automatic IMU mount alignment, 10 Hz position on USB, RTCM corrections in on USB.
- No wheel-tick wire, so wheel ticks off. Optionally the car's speed is sent from the comma instead ("Speed from the
  car" switch).
- **Output on UART1, UART2 and I2C off.** Nothing is wired there, but at 10 Hz UART1's backlog filled the receiver's
  shared send memory, and it silently dropped messages on USB too. That was why fusion seemed stuck forever.
- Send u-blox settings one key per message when unsure: a message with one key the module doesn't know is refused
  whole.

**Corrections (RTK).** The tablet has internet, so it fetches corrections from an NTRIP caster and the comma passes
them to the receiver. In New York, NYSNet (NYS DOT) gives free accounts; its NTRIP login is separate from the
website login. Use **NTRIP 2.0**: over a phone hotspot, the carrier's web proxy broke NTRIP 1.0, and every connection
got "503 Service Temporarily Unavailable" without ever reaching the caster. StarView tries 2.0 first.

**Mount alignment** needs real turns: two hours of highway curves didn't finish it, three U-turns did. Only a finished
("fine") alignment is kept; StarView saves it in the receiver's flash and stops relearning.

## 5. Pico relay box: turn signals and window close

A Raspberry Pi Pico W (MicroPython; its Wi-Fi isn't used) drives a 4-channel 5 V relay module. The comma sends it one character over USB
serial; if it hears nothing valid for 1 second, every relay opens. Only one relay can be on at a time.

Wiring (low-level-trigger module, JD-VCC jumper removed):

```
Pico 3V3(OUT) pin 36 -> module VCC        Pico VBUS pin 40 -> module JD-VCC        Pico GND pin 38 -> module GND
GP2 (pin 4) -> IN1 = left signal          GP3 (pin 5) -> IN2 = right signal       GP4 (pin 6) -> IN3 = window close
Relays 1 and 2: COM -> the turn-signal switch's common (EL), NO1 -> TL (left), NO2 -> TR (right)
Relay 3: COM / NO across the driver's door key switch, lock side, and its ground
Never use the NC contacts.
```

The program is `starpilot/tools/pico/main.py` in tsk-go/openpilot (load it with `pico/load.py`). To try it by hand:
`starpilot/tools/blink.py tap-left | tap-right | left 5 | window 10 | off`.

**What uses it:**
- **Turn signals.** A tap gives the Sienna's one-touch three blinks; a held signal stays on until released. The comma
  sees the blinker on the car's CAN about 0.1 s after the relay closes. StarView uses it for hold-to-blink buttons,
  for lane changes you confirm on the tablet (a tap, or a light nudge of the wheel toward that side), for signalling
  before the route's turns and exits (held through red lights, off once the car has turned), and for passing slower
  cars. It waits while the blind spot is occupied.
- **Window close.** With the car off, holding the driver's door key on "lock" makes the Sienna close every window
  and the sunroof (with its own anti-pinch) and lock the doors. Relay 3 does that for StarLock's "close windows" and
  for a long press on the key fob (below). It refuses while openpilot is running.

## 6. Phone: StarLock and the hotspot

The phone shares its internet with the comma and the tablet through its hotspot. Some phones switch the hotspot off
after a few minutes with nothing connected.

**StarLock** locks and unlocks the car **while it's running** (parked, stopped), which the Sienna won't do from its
own fob. It reaches the comma over Wi-Fi and Bluetooth LE at the same time, whichever answers first. It has a
quick-settings tile and the horn's answer-back (pattern adjustable). It shows each door's real lock state, can lock
when you walk away (from the phone's Bluetooth signal) and optionally unlock when you come back, and has hold to close
the windows.

How it works on the Sienna: the body module refuses the usual diagnostic door command, but accepts Toyota's "active
test" for door lock / unlock (the same one Toyota's dealer tool uses). The comma sends it over openpilot's own CAN link,
only in Park, stopped, with no gas pedal, and hands control back at once if that changes. The panda's safety code
normally blocks such messages, so this fork ships its own panda firmware that allows exactly those frames (built
from the fork's source). Pausing openpilot's CAN link for the test instead (the dealer tool's way) faulted the
Sienna's cruise control, so that's never done.

## 7. The key fob while the car runs

The RTL-SDR on the hub listens to the key fob, only while the car is on in Park (radio off while driving). A press of
a fob you taught it flips the locks within about a second, through the same door command as StarLock. With the car
off (for as long as the hub keeps its power, see Power management), holding any fob button 2.5 s closes the windows
through relay 3. Set up in StarView › Device › Driving › **Key fob** ("Learn" a fob). Off for good with
`/data/starview/no_fob`. It needs `rtl_sdr` (librtlsdr) on the comma.

**Know this before using it:** the fob's rolling code is encrypted, so the comma recognizes your fob by its fixed ID,
which isn't secret. An exact replay of a press it already heard is ignored, but someone who recorded your fob could
make the comma toggle your locks while the car is running. Use it only if that's acceptable to you.

## 8. Chestnut eGPU

The Chestnut is StarPilot's external GPU option: the big driving model runs on an AMD GPU over USB. Our fork adds
two fixes for it until upstream fixes them: the big model's compiled programs are cached across reboots instead of
compiling at every start, and openpilot waits until the big model runs steadily before allowing engagement (otherwise
"Driving Model Lagging" right after it loads). While it loads, StarView's GPU icon shows it and the tablet stays quiet.

## 9. Comma offline mode

StarView › Device › Network › **Comma offline** cuts the comma off from the internet except for what it needs:
software and AGNOS updates, Mapbox (maps, routes, live traffic), time servers, and the tablet and phone on their local
links. Model and sound downloads open a temporary "door" while they run. The comma's kernel has no nf_tables, so it's
done with routing rules, and a small boot service puts them back after every reboot. The clock is kept right too:
parked with no GPS and no time servers, it once fell months behind, and every secure connection failed.

## 10. Smaller things

- **Doors & belts** (StarView): every door and seat-belt switch, read from each drive's logs after the drive. Nothing
  extra runs while driving. Switches the car doesn't report in the standard messages are learned once from the tablet.
- **New ideas** (StarView): the tablet watches for speed bumps, potholes, cut-ins and lights, and may only make
  openpilot gentler (a lower speed cap, a longer following distance, a small offset in the lane), never faster.
- **Drive replay** on the tablet, and a terminal to the comma.

## What we learned

| Problem | Cause | Fix |
| --- | --- | --- |
| Comma GPS lost most satellites with the tablet plugged in | noisy hub power supply | replaced the supply |
| Take-over alerts when recording to the USB stick | the stick pausing stalled recording | record internally, copy while parked |
| USB stick dropping off | shared hub port next to the eGPU | separate port |
| Hub drained the battery and the comma kept restarting | comma back-feeding 5 V to the hub with the car off | comma switches its port's 5 V off after a delay |
| RTK fusion "initializing" forever | old firmware (HPS 1.00); then UART1 output filling the send memory | firmware HPS 1.30; UART/I2C output off |
| NTRIP caster answered 503 | carrier proxy on the phone hotspot breaks NTRIP 1.0 | NTRIP 2.0 |
| Door command refused (`7F 30 11`) | the Sienna's body module wants Toyota's UDS active test | UDS active test, panda firmware allowing those frames |
| Cruise faulted when pausing the CAN link for the door test | the car notices the missing messages | send it over openpilot's own link instead |
| Fob presses detected seconds late or not at all | wideband noise in the van near 312 MHz looked like presses | each fob channel compared with the bands beside it |
| Clock months behind in offline mode | time servers blocked | let the time servers through, fix the clock from a web server's date |
| StarView didn't reopen after updating itself | Android blocks background apps from opening their screen | a one-pixel window while it starts, with "display over other apps" |

## Where the details are

- tsk-go/openpilot `CLAUDE.md`: every comma-side patch, where it lives and why.
- `docs/STARVIEW-APP.md` here: the app's record (releases, automatic updates, every version).
- `docs/tools/` here: how the app's source was recovered and checked.

Left out on purpose: anything private (locations, device IDs, keys, logins, fob IDs), and unfinished research on the
steering's own firmware.
