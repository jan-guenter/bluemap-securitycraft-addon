# SecurityCraft disguise staging gallery

This directory defines a tiny deterministic datapack for the SecurityCraft
disguise-replacement prototype. It is confined to inclusive x `160..191`, y
`99..108`, z `160..175` and does not touch production or cluster state.

The five anchors are:

| Anchor | Position | SecurityCraft state | Expected appearance | Reference |
| --- | --- | --- | --- | --- |
| A1 | `164 100 164` | bare `securitycraft:keypad` | stock keypad | none |
| A2 | `170 100 164` | keypad with disguise module | bricks | `170 100 168` |
| A3 | `176 100 164` | keypad with disguise module | oak log, `axis=x` | `176 100 168` |
| A4 | `182 100 164` | north-facing security camera with disguise module | east-facing bottom oak stairs | `182 100 168` |
| A5 | `188 100 164` | upward sender redstone interface with disguise module | glass | `188 100 168` |

A4 has smooth-stone supports immediately south of the camera and reference at
`182 100 165` and `182 100 169`. The generator writes the requested
`disguiseEnabled`/`Modules` payload verbatim; saved states with properties use
string-valued `Properties` entries.

## Generate, lint, and package

Run from the repository root:

```text
PYTHONDONTWRITEBYTECODE=1 python3 gallery/generate.py --check
PYTHONDONTWRITEBYTECODE=1 python3 gallery/lint.py
bash gallery/package.sh /tmp/bluemap-securitycraft-gallery.zip
```

Running `gallery/generate.py` without `--check` rewrites only the generated
datapack files and `SHA256SUMS`. Packaging checks the generator, linter, and
checksums, then uses sorted paths, stripped ZIP metadata, and a fixed DOS epoch.
No SecurityCraft or Minecraft resource is bundled.

## Staging functions

```text
/function securitycraft_gallery:build
/function securitycraft_gallery:verify
/function securitycraft_gallery:clear
/function securitycraft_gallery:release
```

`build` increments a persistent `#builds` score before clearing and placing the
gallery. The verifier requires that counter to equal one, so an accidental
second build is visible. For a deliberate fresh run, reset `#builds` in
objective `sc_gallery` to zero first.

The build verifies immediately and schedules retained-state checks at 20 and
100 ticks. Each phase performs 16 assertions: 11 block states, four exact
disguise payloads, and the one-build counter. Require:

```text
#immediate_checked = 16   #immediate_failures = 0
#20t_checked       = 16   #20t_failures       = 0
#100t_checked      = 16   #100t_failures      = 0
```

`release` cancels delayed checks and removes only the pad's forceload ticket;
it deliberately retains the gallery for rendering.
