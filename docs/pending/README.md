# Pending: written, not yet in tsk-go/openpilot

## `openpilot-patch72-reopen.diff` (Nov 11)

Comma-side backstop: starviewd starts StarView again on the tablet when it disappears while the car is on (a crash
mid-drive). Fork history item 42 in the fork's CLAUDE.md, starviewd `Hub._reopen_check`, test `test_reopen.py`.

Not committed because that session had no `GIT_SIGNING_KEY_B64` (every commit to the fork must be signed). To land it,
in a session with the key, on the fork's `Dom` branch:

```
git apply docs/pending/openpilot-patch72-reopen.diff   # from a checkout of tsk-go/openpilot, path adjusted
```

Before committing, change one thing the owner was promised: leave StarView alone when another app is in front
(`_tablet_front()` returns another package that isn't the launcher), so it never pops up over an app he's using. Then
update the CLAUDE.md entry to say so, run the test, sign, push, and delete this file.
